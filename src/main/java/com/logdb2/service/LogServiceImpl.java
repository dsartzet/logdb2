package com.logdb2.service;


import com.logdb2.dto.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LogServiceImpl implements LogService {
    @Override
    public List<LogTypeCounterPairResponseDto> totalLogsPerTypeCreatedWithinTimeRangeDesc(Date start, Date stop) {
        return null;
    }

    @Override
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

    @Override
    public void createOrUpdate(LogDto logDto) {

    }

    @Override
    public void upvote(long clientId, long logId) {

    }
}
