package com.logdb2;

import com.github.javafaker.Faker;
import com.logdb2.document.*;
import com.logdb2.repository.AdminRepository;
import com.logdb2.repository.LogRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class LogDbApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(LogDbApplication.class);
	private static final String ACCESS_LOG_PATH = "logs/access.log";
	private static final String HDFS_DATAXCEIVER_LOG_PATH = "logs/HDFS_DataXceiver.log";
	private static final String HDFS_FS_NAMESYSTEM_LOG_PATH = "logs/HDFS_FS_Namesystem.log";
	private static final String ACCESS_LOGS_TIME_PATTERN = "dd/MMM/yyyy:HH:mm:ss Z";
	private static final String HDFS_TIME_PATTERN = "ddMMyy HHmmss";
	private static final DateTimeFormatter ACCESS_LOGS_TIME_FORMATTER = DateTimeFormatter.ofPattern(ACCESS_LOGS_TIME_PATTERN);
	private static final DateTimeFormatter HDFS_TIME_FORMATTER = DateTimeFormatter.ofPattern(HDFS_TIME_PATTERN);
	private static final String ACCESS_LOGS_REGEX = "^(\\S+) - (\\S+) \\[(.+?(?=]))\\] \"(\\S+) (\\S+)\\s+(\\S+)\" (\\S+) (\\S+) \"(.*?(?=\"))\" \"(.*?)\" \"-\"$";
	private static final String RECEIVING_RECEIVED_REGEX = "^(\\S+ \\S+).*DataXceiver: (\\S+) block blk_(\\S+) src: \\/(\\S+):(\\S+) dest: \\/(\\S+):(\\S+)(.*)$";
	private static final String SERVED_REGEX = "^(\\S+ \\S+).*DataXceiver: (\\S+):(\\S+) Served block blk_(\\S+) to \\/(\\S+)$";
	private static final String REPLICATE_REGEX = "^(\\S+ \\S+).*FSNamesystem: BLOCK\\* ask (\\S+):(\\S+) to replicate blk_(\\S+) to datanode\\(s\\) (.*)$";
	private static final String DELETE_REGEX = "^(\\S+ \\S+).*FSNamesystem: BLOCK\\* ask (\\S+):(\\S+) to delete  (.*)$";
	private static final Pattern ACCESS_LOGS_PATTERN = Pattern.compile(ACCESS_LOGS_REGEX);
	private static final Pattern RECEIVING_RECEIVED_PATTERN = Pattern.compile(RECEIVING_RECEIVED_REGEX);
	private static final Pattern SERVED_PATTERN = Pattern.compile(SERVED_REGEX);
	private static final Pattern REPLICATE_PATTERN = Pattern.compile(REPLICATE_REGEX);
	private static final Pattern DELETE_PATTERN = Pattern.compile(DELETE_REGEX);
	private static final int LOCAL_ZONEID = 2;
	private static final int BATCH_SIZE = 300_000;
	private static final List<String> HTTP_METHOD_NAMES = Stream.of(HttpMethodEnum.values()).map(HttpMethodEnum::name).collect(Collectors.toList());

	public static void main(String[] args) {
		SpringApplication.run(LogDbApplication.class, args);
	}

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public void run(String... strings) {
        parseAccessInsertDB();
        parseDataXceiver();
        parseFsNameSystem();
        generateAdminData();
		logger.info("Successfully parsed/generated and inserted data into mongodb!!!");
	}

	private LocalDateTime toLocalDateTimeFromAccessLog(String timeStr) {
		OffsetDateTime date = OffsetDateTime.parse(timeStr, ACCESS_LOGS_TIME_FORMATTER);
		// apply zone offset in UTC timezone (mongodb does not keep timezones)
		return LocalDateTime.ofInstant(date.plusHours(LOCAL_ZONEID).toInstant(), ZoneOffset.UTC);
	}

	private LocalDateTime toLocalDateTimeFromHDFS(String timeStr) {
		return LocalDateTime.parse(timeStr, HDFS_TIME_FORMATTER).plusHours(LOCAL_ZONEID);
	}

	private void parseAccessInsertDB() {
		File file = new File(getClass().getClassLoader().getResource(ACCESS_LOG_PATH).getFile());
		List<Log> logList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int batchCounter = 0;
			while ((line = br.readLine()) != null) {
				batchCounter++;
				final Matcher matcher = ACCESS_LOGS_PATTERN.matcher(line);
				if (matcher.find()) {
					String source_ip = matcher.group(1);
					String userId = matcher.group(2);
					String timestamp = matcher.group(3);
					String httpMethodStr = matcher.group(4).toUpperCase();
					Integer httpMethod = httpMethodStr.length() > 10 || !HTTP_METHOD_NAMES.contains(httpMethodStr) ?
							null : HttpMethodEnum.valueOf(httpMethodStr).getValue();
					String resource = matcher.group(5);
//					String httpVersion = matcher.group(6);
					String status = matcher.group(7);
					Long size = matcher.group(8).equals("-") ? null : Long.parseLong(matcher.group(8));
					String referer = matcher.group(9).equals("-") ? null : matcher.group(9);
					String userAgent = matcher.group(10);

					Access access = new Access();
					access.setSourceIp(source_ip);
					access.setUserId(userId);
					access.setTimestamp(toLocalDateTimeFromAccessLog(timestamp));
					access.setHttpMethod(httpMethod);
					access.setResource(resource);
					access.setStatus(Integer.parseInt(status));
					access.setSize(size);
					access.setReferer(referer);
					access.setUserAgent(userAgent);
					access.setType("access");

					access.setUpvoters(new ArrayList<>());
					logList.add(access);
				} else {
					logger.warn(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), ACCESS_LOGS_PATTERN));
				}
				if (batchCounter == BATCH_SIZE) {
					logRepository.saveAll(logList);
					logList.clear();
					batchCounter = 0;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!logList.isEmpty()) {
			logRepository.saveAll(logList);
		}
	}

	private void parseDataXceiver() {
		File file = new File(getClass().getClassLoader().getResource(HDFS_DATAXCEIVER_LOG_PATH).getFile());
		List<Log> logList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int batchCounter = 0;
			while ((line = br.readLine()) != null) {
				batchCounter++;
				String source_ip = null;
				String type = null;
				String timestamp = null;
				Long size = null;
				String block_id = null;
				String destination_ip = null;
				if (line.contains("Served")) {
					final Matcher matcher = SERVED_PATTERN.matcher(line);
					if (matcher.find()) {
						timestamp = matcher.group(1);
						source_ip = matcher.group(2);
//						String source_port = matcher.group(3);
						block_id = matcher.group(4);
						// only destination ip here
						destination_ip = matcher.group(5);
						type = "served";
						size = null;
					} else {
						logger.warn(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), SERVED_PATTERN));
					}
				} else if (line.contains("Receiving") || line.contains("Received")) {
					final Matcher matcher = RECEIVING_RECEIVED_PATTERN.matcher(line);
					if (matcher.find()) {
						timestamp = matcher.group(1);
						type = matcher.group(2).toLowerCase();
						block_id = matcher.group(3);
						source_ip = matcher.group(4);
//						String source_port = matcher.group(5);
						destination_ip = matcher.group(6);
//						String destination_port = matcher.group(7);
						size = matcher.group(8).isEmpty() ? null : Long.parseLong(matcher.group(8).trim().split("\\s+")[2]);
					} else {
						logger.warn(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), RECEIVING_RECEIVED_PATTERN));
					}
				} else if (line.contains("received exception")
						|| line.contains("Got exception")
						|| line.contains("IOException")) {
					/*  NOOP
					    Ignore lines that contain one of the following:
					    "*received exception*" || "*Got exception*" || "*IOException*"
					*/
				} else {
					logger.warn(String.format("Found unmatched line %s in %s", line, file.getName()));
				}
				// timestamp == null means ignored line
				if (timestamp != null) {
					Dataxceiver dataxceiver = new Dataxceiver();
					dataxceiver.setTimestamp(toLocalDateTimeFromHDFS(timestamp));
					dataxceiver.setType(type);
					dataxceiver.setSourceIp(source_ip);
					dataxceiver.setSize(size);
					dataxceiver.setBlockId(Long.parseLong(block_id));
					dataxceiver.setDestinationIp(destination_ip);

					dataxceiver.setUpvoters(new ArrayList<>());
					logList.add(dataxceiver);
					if (batchCounter == BATCH_SIZE) {
						logRepository.saveAll(logList);
						logList.clear();
						batchCounter = 0;
					}
				}
			}
			if (!logList.isEmpty()) {
				logRepository.saveAll(logList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseFsNameSystem() {
		File file = new File(getClass().getClassLoader().getResource(HDFS_FS_NAMESYSTEM_LOG_PATH).getFile());
		List<Log> logList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int batchCounter = 0;
			while ((line = br.readLine()) != null) {
				batchCounter++;
				String source_ip = null;
				String type = null;
				String timestamp = null;
				Long size = null;
				List<String> block_id_list = new ArrayList<>();
				List<String> destination_ip_list = new ArrayList<>();
				if (line.contains("replicate")) {
					final Matcher matcher = REPLICATE_PATTERN.matcher(line);
					if (matcher.find()) {
						type = "replicate";
						timestamp = matcher.group(1);
						source_ip = matcher.group(2);
//						String source_port = matcher.group(3);
						block_id_list.add(matcher.group(4));
						String[] destination_ips = matcher.group(5).split("\\s+");
						for (String element : destination_ips) {
							destination_ip_list.add(element.split(":")[0]);
						}
					} else {
						logger.warn(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), REPLICATE_PATTERN));
					}
				} else if (line.contains("delete")) {
					final Matcher matcher = DELETE_PATTERN.matcher(line);
					if (matcher.find()) {
						type = "delete";
						timestamp = matcher.group(1);
						source_ip = matcher.group(2);
//						String source_port = matcher.group(3);
						block_id_list = Arrays
								.stream(matcher.group(4).split("\\s+"))
								.map(blk -> blk.replace("blk_", ""))
								.collect(Collectors.toList());
					} else {
						logger.warn(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), DELETE_PATTERN));
					}
				} else {
					logger.warn(String.format("Found unmatched line %s in %s", line, file.getName()));
				}
				Namesystem namesystemDocument = new Namesystem();
				namesystemDocument.setTimestamp(toLocalDateTimeFromHDFS(timestamp));
				namesystemDocument.setType(type);
				namesystemDocument.setSourceIp(source_ip);
				namesystemDocument.setSize(size);
				namesystemDocument.setBlockIds(block_id_list.stream().map(Long::parseLong).collect(Collectors.toList()));
				namesystemDocument.setDestinationIps(destination_ip_list);

				namesystemDocument.setUpvoters(new ArrayList<>());
				logList.add(namesystemDocument);
				if (batchCounter == BATCH_SIZE) {
					logRepository.saveAll(logList);
					logList.clear();
					batchCounter = 0;
				}
			}
			if (!logList.isEmpty()) {
				logRepository.saveAll(logList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateAdminData() {
		Faker faker = new Faker();
		Query query = new Query();
		query.fields().include("_id");
		List<ObjectId> objectIdList = mongoTemplate.find(query, Log.class)
				.stream()
				.map(Log::get_id)
				.collect(Collectors.toList());
		int dbSize = objectIdList.size();
		int requiredSize = dbSize/3 + 1;
		Random random = new Random();

		for (int i=0; i<10; i++) {
			Collections.shuffle(objectIdList);
			int upvoteSum = 0;
			List<Admin> adminList = new ArrayList<>();
			Map<ObjectId, Log> logMap = new HashMap<>();

			while (true) {
				int upvoteSize = random.nextInt(1000) + 1;

				Admin admin = new Admin();
				admin.setUsername(faker.name().username());
				admin.setEmail(faker.internet().emailAddress());
				admin.setPhoneNumber(faker.phoneNumber().phoneNumber());
				LocalDateTime localDateTime = LocalDateTime.of(
						2020, 1, random.nextInt(31) + 1,
						LOCAL_ZONEID,0,0,0
				);
				admin.setTimestamp(localDateTime);
				List<Upvote> adminObjectIdList = new ArrayList<>();
				while (upvoteSize > 0) {
					Log log = logMap.get(objectIdList.get(upvoteSum));
					log = Objects.isNull(log) ? logRepository.findById(objectIdList.get(upvoteSum)).get() : log;
					if(Objects.nonNull(log)) {
						adminObjectIdList.add(new Upvote(objectIdList.get(upvoteSum), log.getSourceIp()));
						log.getUpvoters().add(new Upvoter(admin.getUsername(), admin.getEmail()));
						logMap.put(objectIdList.get(upvoteSum), log);
					}
					upvoteSum++;
					upvoteSize--;
				}
				admin.setUpvotes(adminObjectIdList);
				adminList.add(admin);

				if (upvoteSum > requiredSize) {
					break;
				}
			}
			adminRepository.saveAll(adminList);
			logRepository.saveAll(logMap.values());
		}
	}
}
