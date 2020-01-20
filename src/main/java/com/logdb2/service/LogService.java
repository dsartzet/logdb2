package com.logdb2.service;

import com.logdb2.document.Log;
import com.logdb2.result.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LogService {
    List<LogTypeTotalResult> totalLogsPerTypeCreatedWithinTimeRangeDesc(LocalDateTime start, LocalDateTime stop);

    List<LogDateTotalResult> totalRequestsPerDayForTypeAndTimeRange(String logType, LocalDateTime start, LocalDateTime stop);

    List<LogSourceIpTotalResult> threeMostCommonLogsPerSourceIpFor(LocalDate date);

    List<String> twoLeastCommonHttpMethodsInTimeRange(LocalDateTime start, LocalDateTime stop);

    List<LogRefererResult> referersWithResources();

    List<BlocksSameDayReplicateAndServedResult> blocksReplicatedAndServedSameDay();

    List<LogIdTotalResult> fiftyMostUpvotedLogsFor(LocalDate date);

    List<LogBlockIdResult> blocksInUpvotedLogBy(String username);

    Log createOrUpdate(Log log);

    Log createOrUpdateDirtyOnly(Log log);

    List<SameEmailDifferentUsernamesUpvotedLogsResult> sameEmailDifferentUsernamesUpvotedLogs();
}

