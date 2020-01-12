package com.logdb2.service;

import com.logdb2.document.Log;
import com.logdb2.result.LogBlockIdResult;
import com.logdb2.result.LogDateTotalResult;
import com.logdb2.result.LogIdTotalResult;
import com.logdb2.result.LogRefererResult;
import com.logdb2.result.LogSourceIpTotalResult;
import com.logdb2.result.LogTypeTotalResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LogService {
    List<LogTypeTotalResult> totalLogsPerTypeCreatedWithinTimeRangeDesc(LocalDateTime start, LocalDateTime stop);

    List<LogDateTotalResult> totalRequestsPerDayForTypeAndTimeRange(String logType, LocalDateTime start, LocalDateTime stop);

    List<LogSourceIpTotalResult> threeMostCommonLogsPerSourceIpFor(LocalDate date);

    List<String> twoLeastCommonHttpMethodsInTimeRange(LocalDateTime start, LocalDateTime stop);

    List<LogRefererResult> referersWithResources();

    List<LogBlockIdResult> blocksReplicatedAndServedSameDay();

    List<LogIdTotalResult> fiftyMostUpvotedLogsFor(LocalDate date);

 //   List<ClientDto> mostUpvotesGiven();

 //   List<ClientDto> mostUpvotesInDifferentIps();

  // List<LogDto> logsWithSameEmailUpvotes();

  //  List<Integer> blocksInUpvotedLogBy(String username);

    void createOrUpdate(Log log);
}

