package com.logdb2.service;


import com.logdb2.document.HttpMethodEnum;
import com.logdb2.document.Log;
import com.logdb2.repository.LogRepository;
import com.logdb2.result.LogBlockIdResult;
import com.logdb2.result.LogDateTotalResult;
import com.logdb2.result.LogHttpMethodResult;
import com.logdb2.result.LogIdTotalResult;
import com.logdb2.result.LogRefererResult;
import com.logdb2.result.LogSourceIpTotalResult;
import com.logdb2.result.LogTypeTotalResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

@Component
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository logRepository;

    private static final String LOGS_COLLECTION_NAME = "logs";
    private static final String ADMIN_COLLECTION_NAME = "admin";
    private static final AggregationOptions AGGREGATION_OPTIONS = Aggregation.newAggregationOptions()
            .allowDiskUse(true).build();
    private static final HttpMethodEnum[] HTTP_METHOD_ENUMS = HttpMethodEnum.values();

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<LogTypeTotalResult> totalLogsPerTypeCreatedWithinTimeRangeDesc(LocalDateTime start, LocalDateTime stop) {
        Aggregation agg = newAggregation(
                match(Criteria.where("timestamp")
                        .gte(start)
                        .lt(stop)),
                group("type").count().as("total"),
                project("total").and("type").previousOperation(),
                sort(Sort.Direction.DESC, "total")
        ).withOptions(AGGREGATION_OPTIONS);

        AggregationResults<LogTypeTotalResult> groupResults
                = mongoTemplate.aggregate(agg, LOGS_COLLECTION_NAME, LogTypeTotalResult.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<LogDateTotalResult> totalRequestsPerDayForTypeAndTimeRange(String logType, LocalDateTime start, LocalDateTime stop) {
        Aggregation agg = newAggregation(
                match(new Criteria().andOperator(
                        Criteria.where("type").is(logType),
                        Criteria.where("timestamp").gte(start).lt(stop))),
                group("timestamp").count().as("total"),
                project("total").and("timestamp").previousOperation(),
                sort(Sort.Direction.DESC, "total")
        ).withOptions(AGGREGATION_OPTIONS);

        AggregationResults<LogDateTotalResult> groupResults
                = mongoTemplate.aggregate(agg, LOGS_COLLECTION_NAME, LogDateTotalResult.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<LogSourceIpTotalResult> threeMostCommonLogsPerSourceIpFor(LocalDate date) {
        Aggregation agg = newAggregation(
                match(Criteria.where("timestamp")
                        .gte(LocalDateTime.of(date, LocalTime.MIDNIGHT))
                        .lt(LocalDateTime.of(date, LocalTime.MAX))),
                group("sourceIp").count().as("total"),
                project("total").and("sourceIp").previousOperation(),
                sort(Sort.Direction.DESC, "total"),
                limit(3)
        ).withOptions(AGGREGATION_OPTIONS);

        AggregationResults<LogSourceIpTotalResult> groupResults
                = mongoTemplate.aggregate(agg, LOGS_COLLECTION_NAME, LogSourceIpTotalResult.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<String> twoLeastCommonHttpMethodsInTimeRange(LocalDateTime start, LocalDateTime stop) {
        Aggregation agg = newAggregation(
                match(new Criteria().andOperator(
                        Criteria.where("timestamp").gte(start).lt(stop),
                        Criteria.where("httpMethod").ne(null))),
                group("httpMethod").count().as("total"),
                project("total").and("httpMethod").previousOperation(),
                sort(Sort.Direction.ASC, "total"),
                limit(2)
        ).withOptions(AGGREGATION_OPTIONS);

        AggregationResults<LogHttpMethodResult> groupResults
                = mongoTemplate.aggregate(agg, LOGS_COLLECTION_NAME, LogHttpMethodResult.class);
        return groupResults.getMappedResults()
                .stream()
                .map(e -> HTTP_METHOD_ENUMS[e.getHttpMethod()].name())
                .collect(Collectors.toList());
    }

    @Override
    public List<LogRefererResult> referersWithResources() {
        Aggregation agg = newAggregation(
                match(Criteria.where("referer").ne(null)),
                group("referer").addToSet("resource").as("resources"),
                unwind("resources"),
                group("_id").count().as("total"),
                match(Criteria.where("total").gt(1)),
                project("total").and("referer").previousOperation(),
                sort(Sort.Direction.DESC, "total")
        );

        AggregationResults<LogRefererResult> groupResults
                = mongoTemplate.aggregate(agg, LOGS_COLLECTION_NAME, LogRefererResult.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<LogBlockIdResult> blocksReplicatedAndServedSameDay() {
        return null;
    }

    @Override
    public List<LogIdTotalResult> fiftyMostUpvotedLogsFor(LocalDate date) {
        Aggregation agg = newAggregation(
                match(Criteria.where("timestamp")
                                .gte(LocalDateTime.of(date, LocalTime.MIDNIGHT))
                                .lt(LocalDateTime.of(date, LocalTime.MAX))),
                unwind("upvotes"),
                group("upvotes").count().as("total"),
                project("total").and("upvotes").previousOperation(),
                sort(Sort.Direction.DESC, "total"),
                limit(50)
        ).withOptions(AGGREGATION_OPTIONS);

        AggregationResults<LogIdTotalResult> groupResults
                = mongoTemplate.aggregate(agg, ADMIN_COLLECTION_NAME, LogIdTotalResult.class);
        return groupResults.getMappedResults();
    }


        /*db.getCollection('logs').aggregate
([
    {$unwind: "$blockIds"},
     {$unwind: "$upvoters"},
     { $match : { "upvoters.username" : "matt.mertz" } } ] )*/

    @Override
    public List<Long> blocksInUpvotedLogBy(String username) {
        Aggregation agg = newAggregation(
                unwind("blockIds"),
                unwind("upvoters"),
                match(Criteria.where("upvoters.username").is(username))).withOptions(AGGREGATION_OPTIONS);

        AggregationResults<Long> groupResults
                = mongoTemplate.aggregate(agg, LOGS_COLLECTION_NAME, Long.class);
        return groupResults.getMappedResults();
    }

    @Override
    public void createOrUpdate(Log log) {
        logRepository.save(log);
    }

    @Override
    public List<Log> sameEmailDifferentUsernamesUpvotedLogs() {
        Aggregation agg = newAggregation(
                unwind("upvoters"),
                project("_id").and("upvoters.email").as("email").and("upvoters.email").as("email"),
                        group(Fields.fields("email","_id")).addToSet("username").as("data"),
                        match(Criteria.where("data").gte(2))).withOptions(AGGREGATION_OPTIONS);

        AggregationResults<Log> groupResults
                = mongoTemplate.aggregate(agg, LOGS_COLLECTION_NAME, Log.class);
        return groupResults.getMappedResults();
    }
}
