package com.logdb;

import com.logdb.entity.Access;
import com.logdb.entity.Dataxceiver;
import com.logdb.entity.Namesystem;
import com.logdb.entity.Request;
import com.logdb.entity.Response;
import com.logdb.entity.Session;
import com.logdb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootApplication
public class LogDbApplication implements CommandLineRunner {
	private static final String ACCESS_LOG_PATH = "/home/d.sartzetakis/Desktop/ASKISI/access.log";
	private static final String HDFS_DATAXCEIVER_LOG_PATH = "/home/d.sartzetakis/Desktop/ASKISI/HDFS_DataXceiver.log";
	private static final String HDFS_FS_NAMESYSTEM_LOG_PATH = "/home/d.sartzetakis/Desktop/ASKISI/HDFS_FS_Namesystem.log";
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

	@Autowired
	private AccessRepository accessRepository;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private ResponseRepository responseRepository;
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private DataxceiverRepository dataxceiverRepository;
	@Autowired
	private NamesystemRepository namesystemRepository;

	public static void main(String[] args) {
		SpringApplication.run(LogDbApplication.class, args);
	}

	@Override
	public void run(String... strings) {
		parseAccessInsertDB();
		parseHdfsInsertDB();
	}

	private Timestamp toSqlTimestampFromAccessLog(String timeStr) {
		ZonedDateTime date = ZonedDateTime.parse(timeStr, ACCESS_LOGS_TIME_FORMATTER);
		// apply zone offset in UTC timezone (sql does not keep timezones)
		return Timestamp.valueOf(LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC));
	}

	private Timestamp toSqlTimestampFromHDFS(String timeStr) {
		LocalDateTime date = LocalDateTime.parse(timeStr, HDFS_TIME_FORMATTER);
		return Timestamp.valueOf(date);
	}

	private void parseAccessInsertDB() {
		File file = new File(ACCESS_LOG_PATH);
		List<Access> accessList = new ArrayList<>();
		Map<String,Request> requestMap = new HashMap<>();
		Map<String, Response> responseMap = new HashMap<>();
		Map<String, Session> sessionMap = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				final Matcher matcher = ACCESS_LOGS_PATTERN.matcher(line);
				if (matcher.find()) {
					String source_ip = matcher.group(1);
					String userId = matcher.group(2);
					String timestamp = matcher.group(3);
					String httpMethod = matcher.group(4);
					String resource = matcher.group(5);
					String httpVersion = matcher.group(6);
					String status = matcher.group(7);
					Long size = matcher.group(8).equals("-") ? null : Long.parseLong(matcher.group(8));
					String refer = matcher.group(9);
					String userAgent = matcher.group(10);

					Access access = new Access();
					access.setTimestamp(toSqlTimestampFromAccessLog(timestamp));
					access.setReferer(refer);
					access.setUserId(userId);
					access.setType("access");
					String requestKey = httpMethod + resource;
					String responseKey = "" + status + size;
					String sessionKey = source_ip + userAgent;
					if (!requestMap.containsKey(requestKey)) {
						Request request = new Request();
						request.setHttpMethod(httpMethod.length() > 10 ? null : httpMethod);
						request.setResource(resource);
						requestMap.put(requestKey,request);
					}
					if (!responseMap.containsKey(responseKey)) {
						Response response = new Response();
						response.setSize(size);
						response.setStatus(Integer.parseInt(status));
						responseMap.put(responseKey, response);
					}
					if (!sessionMap.containsKey(sessionKey)) {
						Session session = new Session();
						session.setSourceIp(source_ip);
						session.setUserAgent(userAgent);
						sessionMap.put(sessionKey, session);
					}
					access.setRequest(requestMap.get(requestKey));
					access.setResponse(responseMap.get(responseKey));
					access.setSession(sessionMap.get(sessionKey));
					accessList.add(access);
				} else {
					System.out.println(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), ACCESS_LOGS_PATTERN));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		requestRepository.saveAll(requestMap.values());
		responseRepository.saveAll(responseMap.values());
		sessionRepository.saveAll(sessionMap.values());
		accessRepository.saveAll(accessList);
	}

	private void parseHdfsInsertDB() {
		List<Dataxceiver> dataxceiverList = new ArrayList<>();
		List<Namesystem> namesystemList = new ArrayList<>();
		parseDataXceiver(dataxceiverList);
		parseFsNameSystem(namesystemList);
		dataxceiverRepository.saveAll(dataxceiverList);
		namesystemRepository.saveAll(namesystemList);
	}

	private void parseDataXceiver(List<Dataxceiver> dataxceiverList) {
		File file = new File(HDFS_DATAXCEIVER_LOG_PATH);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String source_ip_port = null;
				String type = null;
				String timestamp = null;
				Long size = null;
				String block_id = null;
				String destination_ip_port = null;
				if (line.contains("Served")) {
					final Matcher matcher = SERVED_PATTERN.matcher(line);
					if (matcher.find()) {
						timestamp = matcher.group(1);
						String source_ip = matcher.group(2);
						String source_port = matcher.group(3);
						source_ip_port = source_ip + ":" + source_port;
						block_id = matcher.group(4);
						// only destination ip here
						destination_ip_port = matcher.group(5);
						type = "Served";
						size = null;
					} else {
						System.out.println(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), SERVED_PATTERN));
					}
				} else if (line.contains("Receiving") || line.contains("Received")) {
					final Matcher matcher = RECEIVING_RECEIVED_PATTERN.matcher(line);
					if (matcher.find()) {
						timestamp = matcher.group(1);
						type = matcher.group(2);
						block_id = matcher.group(3);
						String source_ip = matcher.group(4);
						String source_port = matcher.group(5);
						source_ip_port = source_ip + ":" + source_port;
						String destination_ip = matcher.group(6);
						String destination_port = matcher.group(7);
						destination_ip_port = destination_ip + ":" + destination_port;
						size = matcher.group(8).isEmpty() ? null : Long.parseLong(matcher.group(8).trim().split("\\s+")[2]);
					} else {
						System.out.println(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), RECEIVING_RECEIVED_PATTERN));
					}
				} else if (line.contains("received exception")
							|| line.contains("Got exception")
							|| line.contains("IOException")) {
					/*  NOOP
					    Ignore lines that contain one of the following:
					    "*received exception*" || "*Got exception*" || "*IOException*"
					*/
				} else {
					System.out.println(String.format("Found unmatched line %s in %s", line, file.getName()));
				}
				// timestamp == null means ignored line
				if (timestamp != null) {
					Dataxceiver dataxceiver = new Dataxceiver();
					dataxceiver.setTimestamp(toSqlTimestampFromHDFS(timestamp));
					dataxceiver.setType(type);
					dataxceiver.setSourceIp(source_ip_port);
					dataxceiver.setSize(size);
					dataxceiver.setBlockId(Long.parseLong(block_id));
					dataxceiver.setDestinationIp(destination_ip_port);
					dataxceiverList.add(dataxceiver);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseFsNameSystem(List<Namesystem> namesystemList) {
		File file = new File(HDFS_FS_NAMESYSTEM_LOG_PATH);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String source_ip_port = null;
				String type = null;
				String timestamp = null;
				Long size = null;
				List<String> block_id_list = new ArrayList<>();
				List<String> destination_ip_port_list = new ArrayList<>();
				if (line.contains("replicate")) {
					final Matcher matcher = REPLICATE_PATTERN.matcher(line);
					if (matcher.find()) {
						type = "replicate";
						timestamp = matcher.group(1);
						String source_ip = matcher.group(2);
						String source_port = matcher.group(3);
						source_ip_port = source_ip + source_port;
						block_id_list.add(matcher.group(4));
						destination_ip_port_list = Arrays.asList(matcher.group(5).split("\\s+"));
//						List<String> destination_ip_list = new ArrayList<>();
//						List<String> destination_port_list = new ArrayList<>();
//						for (String element : destination_ips) {
//							String[] elementSplitted = element.split(":");
//							destination_ip_list.add(elementSplitted[0]);
//							destination_port_list.add(elementSplitted[1]);
//						}
					} else {
						System.out.println(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), REPLICATE_PATTERN));
					}
				} else if (line.contains("delete")) {
					final Matcher matcher = DELETE_PATTERN.matcher(line);
					if (matcher.find()) {
						type = "delete";
						timestamp = matcher.group(1);
						String source_ip = matcher.group(2);
						String source_port = matcher.group(3);
						source_ip_port = source_ip + source_port;
						block_id_list = Arrays
											.stream(matcher.group(4).split("\\s+"))
											.map(blk -> blk.replace("blk_", ""))
											.collect(Collectors.toList());
					} else {
						System.out.println(String.format("Couldn't match line %s in %s with pattern %s", line, file.getName(), DELETE_PATTERN));
					}
				} else {
					System.out.println(String.format("Found unmatched line %s in %s", line, file.getName()));
				}
				Namesystem namesystem = new Namesystem();
				namesystem.setTimestamp(toSqlTimestampFromHDFS(timestamp));
				namesystem.setType(type);
				namesystem.setSourceIp(source_ip_port);
				namesystem.setSize(size);
				namesystem.setBlockIds(block_id_list.stream().map(Long::parseLong).collect(Collectors.toList()));
				namesystem.setDestinationIps(destination_ip_port_list);
				namesystemList.add(namesystem);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
