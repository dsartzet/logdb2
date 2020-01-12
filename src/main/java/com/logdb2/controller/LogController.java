package com.logdb2.controller;

import com.logdb2.document.Access;
import com.logdb2.document.Dataxceiver;
import com.logdb2.document.Namesystem;
import com.logdb2.result.*;
import com.logdb2.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class LogController {

    @Autowired
    LogService logService;

    /**
     * 1. Find the total logs per type that were created within a specified time range and sort them in
     * a descending order. Please note that individual files may log actions of more than one type.
     * e.g. http://localhost:9090/logs/total/per-type/created?start=2015-01-13T00:00:00&stop=2020-01-01T00:00:00
     */
    @RequestMapping(value = "/logs/total/per-type/created", method = RequestMethod.GET)
    @ResponseBody
    List<LogTypeTotalResult> totalLogsPerTypeCreatedWithinTimeRangeDesc(
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(name = "stop") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime stop) {
        return logService.totalLogsPerTypeCreatedWithinTimeRangeDesc(start, stop);
    }

    /**
     * 2. Find the number of total requests per day for a specific log type and time range.
     * e.g http://localhost:9090/requests/total/per-day?start=2016-01-13T00:00:00&stop=2020-01-01T00:00:00&log-type=access
     */
    @RequestMapping(value = "/requests/total/per-day", method = RequestMethod.GET)
    @ResponseBody
    List<LogDateTotalResult> totalRequestsPerDayForTypeAndTimeRange(
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(name = "stop") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime stop,
            @RequestParam(name = "log-type") String logType) {
        return logService.totalRequestsPerDayForTypeAndTimeRange(logType, start, stop);
    }

    /**
     * 3. Find the three most common logs per source IP for a specific day.
     * e.g. http://localhost:9090/logs/three-most-common/per-source-ip?date=2015-12-12
     */
    @RequestMapping(value = "/logs/three-most-common/per-source-ip", method = RequestMethod.GET)
    @ResponseBody
    List<LogSourceIpTotalResult> mostCommonLogsPerSourceIpFor(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return logService.threeMostCommonLogsPerSourceIpFor(date);
    }

    /**
     * 4. Find the two least common HTTP methods with regards to a given time range.
     * e.g. http://localhost:9090/http-methods/two-least-common?start=2015-01-13T00:00:00&stop=2020-01-01T00:00:00
     */
    @RequestMapping(value = "/http-methods/two-least-common", method = RequestMethod.GET)
    @ResponseBody
    List<String> leastCommonHttpMethodsInTimeRange(
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(name = "stop") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime stop
    ) {
        return logService.twoLeastCommonHttpMethodsInTimeRange(start, stop);
    }

    /**
     * 5. Find the referrers (if any) that have led to more than one resources.
     * e.g. http://localhost:9090/referers/with-resources
     */
    @RequestMapping(value = "/referers/with-resources", method = RequestMethod.GET)
    @ResponseBody
    List<LogRefererResult> referersWithResources() {
        return logService.referersWithResources();
    }

    /**
     * 6. Find the blocks that have been replicated the same day that they have also been served.
     * e.g. http://localhost:9090/blocks/replicated-served-same-day
     */
    @RequestMapping(value = "/blocks/replicated-served-same-day", method = RequestMethod.GET)
    @ResponseBody
    List<LogBlockIdResult> blocksReplicatedAndServedSameDay() {
        return logService.blocksReplicatedAndServedSameDay();
    }

    /*7. Find the fifty most upvoted logs for a specific day.*/

    /**
     * 7. Find the fifty most upvoted logs for a specific day.
     * e.g. http://localhost:9090/logs/fifty-most-upvoted?date=2020-01-01
     */
    @RequestMapping(value = "/logs/fifty-most-upvoted", method = RequestMethod.GET)
    @ResponseBody
    List<LogIdTotalResult> mostUpvotedLogsFor(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return  logService.fiftyMostUpvotedLogsFor(date);
    }

    /*8. Find the fifty most active administrators, with regard to the total number of upvotes.*/

//    @RequestMapping(value = "/admins/fifty-most-active", method = RequestMethod.GET)
//    @ResponseBody
//    List<ClientDto> mostUpvotesGiven() {
//        return logService.mostUpvotesGiven();
//    }

    /*9. Find the top fifty administrators, with regard to the total number of source IPs for which
        they have upvoted logs.*/

    // TODO change request mapping value
//    @RequestMapping(value = "/admins/most-upvotes-ips/top-fifty", method = RequestMethod.GET)
//    @ResponseBody
//    List<ClientDto> mostUpvotesInDifferentIps() {
//    return logService.mostUpvotesInDifferentIps();
//    }

    /*10. Find all logs for which the same e-mail has been used for more than one usernames when
        casting an upvote.*/

//    @RequestMapping(value = "/admins/most-upvotes-ips/top-fifty", method = RequestMethod.GET)
//    @ResponseBody
//    List<LogDto> logsWithSameEmailUpvotes() {
//        return logService.logsWithSameEmailUpvotes();
//    }

    /*11. Find all the block ids for which a given name has casted a vote for a log involving it.*/

/*    @RequestMapping(value = "/blocks/in-upvoted-logs", method = RequestMethod.GET)
    @ResponseBody
    List<Integer> blocksInUpvotedLogBy(@RequestParam("username") String username) {
        return logService.blocksInUpvotedLogBy(username);
    }*/

    @RequestMapping(value = "/logs/access/", method = RequestMethod.POST)
    void accessCreateOrUpdate(@RequestBody Access log) {
        logService.createOrUpdate(log);
    }

    @RequestMapping(value = "/logs/dataxceiver/", method = RequestMethod.POST)
    void dataxceiverCreateOrUpdate(@RequestBody Dataxceiver log) {
        logService.createOrUpdate(log);
    }

    @RequestMapping(value = "/logs/namesystem/", method = RequestMethod.POST)
    void namesystemCreateOrUpdate(@RequestBody Namesystem log) {
        logService.createOrUpdate(log);
    }

}
