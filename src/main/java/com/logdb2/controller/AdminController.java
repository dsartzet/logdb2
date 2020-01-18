package com.logdb2.controller;

import com.logdb2.result.MostUpvotesInDifferentIpsResult;
import com.logdb2.result.MostUpvotesResult;
import com.logdb2.service.AdminService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.ValidationException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * etc. Admins upvotes a log.
     * e.g. http://localhost:9090/admins/{adminId}/upvote/{logId}
     */
    @RequestMapping(path = "/admins/{adminId}/upvote/{logId}", method = RequestMethod.POST)
    void upvote(@PathVariable("adminId") String adminId, @PathVariable("logId") String logId ) throws ValidationException {
        adminService.upvote( new ObjectId(adminId),  new ObjectId(logId));
    }


    /**
     * 8. Find the fifty most active administrators, with regard to the total number of upvotes.
     * e.g. http://localhost:9090/admins/fifty-most-active
     */
    @RequestMapping(value = "/admins/fifty-most-active", method = RequestMethod.GET)
    @ResponseBody
    List<MostUpvotesResult> mostUpvotesGiven() {
       return adminService.mostUpvotesGiven();
    }



    /**
     * 9. Find the top fifty administrators, with regard to the total number of source IPs for which
     * they have upvoted logs.
     * e.g. http://localhost:9090/admins/most-upvotes-ips/top-fifty
     */
    @RequestMapping(value = "/admins/most-upvotes-ips/top-fifty", method = RequestMethod.GET)
    @ResponseBody
    List<MostUpvotesInDifferentIpsResult> mostUpvotesInDifferentIps() {
        return adminService.mostUpvotesInDifferentIps();
    }

}
