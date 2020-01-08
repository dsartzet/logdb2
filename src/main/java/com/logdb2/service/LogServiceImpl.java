package com.logdb2.service;


import com.logdb2.document.Log;
import com.logdb2.dto.*;
import com.logdb2.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository logRepository;

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
    public void createOrUpdate(Log log) {
        logRepository.save(log);
    }
}
