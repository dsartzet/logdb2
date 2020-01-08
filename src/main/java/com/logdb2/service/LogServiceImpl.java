package com.logdb2.service;


import com.logdb2.document.Log;
import com.logdb2.result.LogTypeTotalResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import com.logdb2.repository.LogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

@Component
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository logRepository;

    private static final String LOGS_COLLECTION_NAME = "logs";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<LogTypeTotalResult> totalLogsPerTypeCreatedWithinTimeRangeDesc(LocalDate start, LocalDate stop) {
        Aggregation agg = newAggregation(
                match(Criteria.where("timestamp")
                        .gte(start)
                        .lt(stop)),
                group("type").count().as("total"),
                project("total").and("type").previousOperation(),
                sort(Sort.Direction.DESC, "total")
        );

        AggregationResults<LogTypeTotalResult> groupResults
                = mongoTemplate.aggregate(agg, LOGS_COLLECTION_NAME, LogTypeTotalResult.class);
        return groupResults.getMappedResults();
    }

  /*  @Override
    public List<RequestsPerDayCounterResponseDto> totalRequestsPerDayForTypeAndTimeRange(String logType, Date start, Date stop) {
        return null;
    }

    @Override
    public List<MostCommonLogsIpDateResponseDto> mostCommonLogsPerSourceIpFor(Date date) {
        return null;
    }

    @Override
    public List<String> leastCommonHttpMethodsInTimeRange(Date start, Date stop) {
        return null;
    }

    @Override
    public List<String> referrersWithResources() {
        return null;
    }

    @Override
    public List<Integer> blocksReplicatedAndServedSameDay() {
        return null;
    }

    @Override
    public List<LogDto> mostUpvotedLogsFor(Date date) {
        return null;
    }

    @Override
    public List<ClientDto> mostUpvotesGiven() {
        return null;
    }

    @Override
    public List<ClientDto> mostUpvotesInDifferentIps() {
        return null;
    }

    @Override
    public List<LogDto> logsWithSameEmailUpvotes() {
        return null;
    }

    @Override
    public List<Integer> blocksInUpvotedLogBy(String username) {
        return null;
    }
*/
    @Override
    public void createOrUpdate(Log log) {
        logRepository.save(log);
    }
}
