package com.logdb2.service;

import com.logdb2.dto.*;

import java.util.Date;
import java.util.List;

public interface LogService {
    List<LogTypeCounterPairResponseDto> totalLogsPerTypeCreatedWithinTimeRangeDesc(Date start, Date stop);

    List<RequestsPerDayCounterResponseDto> totalRequestsPerDayForTypeAndTimeRange(String logType, Date start, Date stop);

    List<MostCommonLogsIpDateResponseDto> mostCommonLogsPerSourceIpFor(Date date);

    List<String> leastCommonHttpMethodsInTimeRange(Date start, Date stop);

    List<String> referrersWithResources();

    List<Integer> blocksReplicatedAndServedSameDay();

    List<LogDto> mostUpvotedLogsFor(Date date);

    List<ClientDto> mostUpvotesGiven();

    List<ClientDto> mostUpvotesInDifferentIps();

    List<LogDto> logsWithSameEmailUpvotes();

    List<Integer> blocksInUpvotedLogBy(String username);

    void createOrUpdate(LogDto logDto);

    void upvote(long clientId, long logId);
}
