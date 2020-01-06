package com.logdb.controller;


import com.logdb.dto.*;
import com.logdb.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class LogController {


    @Autowired
    LogService logService;

    /*1. Find the total logs per type that were created within a specified time range and sort them in
        a descending order. Please note that individual files may log actions of more than one type.*/

    @RequestMapping(value = "/logs/total/per-type/created", method = RequestMethod.GET)
    @ResponseBody
    List<LogTypeCounterPairResponseDto> totalLogsPerTypeCreatedWithinTimeRangeDesc(@RequestParam("start") Date start, @RequestParam("stop") Date stop) {
        return logService.totalLogsPerTypeCreatedWithinTimeRangeDesc(start, stop);
    }

    /*2.Find the number of total requests per day for a specific log type and time range.*/

    @RequestMapping(value = "/requests/total/per-day", method = RequestMethod.GET)
    @ResponseBody
    List<RequestsPerDayCounterResponseDto> totalRequestsPerDayForTypeAndTimeRange(@RequestParam("log-type")String logType, @RequestParam("start") Date start,  @RequestParam("stop") Date stop) {
        return logService.totalRequestsPerDayForTypeAndTimeRange(logType, start, stop);
    }

    /*3. Find the three most common logs per source IP for a specific day.*/

    @RequestMapping(value = "/logs/most-common/per-source-ip", method = RequestMethod.GET)
    @ResponseBody
    List<MostCommonLogsIpDateResponseDto> mostCommonLogsPerSourceIpFor(@RequestParam("date") Date date) {
        return logService.mostCommonLogsPerSourceIpFor(date);
    }

    /*4. Find the two least common HTTP methods with regards to a given time range.*/

    @RequestMapping(value = "/http-methods/least-common", method = RequestMethod.GET)
    @ResponseBody
    List<String> leastCommonHttpMethodsInTimeRange(@RequestParam("start") Date start, @RequestParam("stop")  Date stop) {
        return logService.leastCommonHttpMethodsInTimeRange(start, stop);
    }

    /*5. Find the referrers (if any) that have led to more than one resources*/

    @RequestMapping(value = "/referrers/with-resources", method = RequestMethod.GET)
    @ResponseBody
    List<String> referrersWithResources() {
        return logService.referrersWithResources();
    }

    /*6. Find the blocks that have been replicated the same day that they have also been served.*/

    @RequestMapping(value = "/blocks/replicated-served-same-day", method = RequestMethod.GET)
    @ResponseBody
    List<Integer> blocksReplicatedAndServedSameDay() {
        return logService.blocksReplicatedAndServedSameDay();
    }

    /*7. Find the fifty most upvoted logs for a specific day.*/

    @RequestMapping(value = "/logs/most-upvoted", method = RequestMethod.GET)
    @ResponseBody
    List<LogDto> mostUpvotedLogsFor(@RequestParam("date") Date date) {
        return  logService.mostUpvotedLogsFor(date);
    }

    /*8. Find the fifty most active administrators, with regard to the total number of upvotes.*/

    @RequestMapping(value = "/admins/fifty-most-active", method = RequestMethod.GET)
    @ResponseBody
    List<ClientDto> mostUpvotesGiven() {
        return logService.mostUpvotesGiven();
    }

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

    @RequestMapping(value = "/admins/most-upvotes-ips/top-fifty", method = RequestMethod.GET)
    @ResponseBody
    List<LogDto> logsWithSameEmailUpvotes() {
        return logService.logsWithSameEmailUpvotes();
    }

    /*11. Find all the block ids for which a given name has casted a vote for a log involving it.*/

    @RequestMapping(value = "/blocks/in-upvoted-logs", method = RequestMethod.GET)
    @ResponseBody
    List<Integer> blocksInUpvotedLogBy(@RequestParam("username") String username) {
        return logService.blocksInUpvotedLogBy(username);
    }

    @RequestMapping(value = "/logs/create", method = RequestMethod.POST)
    void createOrUpdate(@RequestBody LogDto logDto) {
        logService.createOrUpdate(logDto);
    }

    @RequestMapping(value = "/logs/{logId}/{clientId}/upvote", method = RequestMethod.POST)
    void upvote(@PathVariable long logId, @PathVariable long clientId) {
        logService.upvote(clientId, logId);
    }

}
