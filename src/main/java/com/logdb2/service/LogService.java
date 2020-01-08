package com.logdb2.service;

import com.logdb2.document.Log;
import com.logdb2.result.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface LogService {
    List<LogTypeTotalResult> totalLogsPerTypeCreatedWithinTimeRangeDesc(LocalDate start, LocalDate stop);

  //  List<RequestsPerDayCounterResponseDto> totalRequestsPerDayForTypeAndTimeRange(String logType, Date start, Date stop);

 //   List<MostCommonLogsIpDateResponseDto> mostCommonLogsPerSourceIpFor(Date date);

  //  List<String> leastCommonHttpMethodsInTimeRange(Date start, Date stop);

 //   List<String> referrersWithResources();

 //   List<Integer> blocksReplicatedAndServedSameDay();

  //  List<LogDto> mostUpvotedLogsFor(Date date);

 //   List<ClientDto> mostUpvotesGiven();

 //   List<ClientDto> mostUpvotesInDifferentIps();

  // List<LogDto> logsWithSameEmailUpvotes();

  //  List<Integer> blocksInUpvotedLogBy(String username);

    void createOrUpdate(Log log);
}

