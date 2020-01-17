package com.logdb2.controller;

import com.logdb2.document.Admin;
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

    @RequestMapping(value = "/admin/{adminId}/upvote/{logId}", method = RequestMethod.POST)
    void upvote(@PathVariable String logId, @PathVariable long adminId) throws ValidationException {
        adminService.upvote(adminId,  new ObjectId(logId));
    }

    @RequestMapping(value = "/admins/fifty-most-active", method = RequestMethod.GET)
    @ResponseBody
    List<Admin> mostUpvotesGiven() {
       return adminService.mostUpvotesGiven();
    }
    @RequestMapping(value = "/admins/most-upvotes-ips/top-fifty", method = RequestMethod.GET)
    @ResponseBody
    List<Admin> mostUpvotesInDifferentIps() {
        return adminService.mostUpvotesInDifferentIps();
    }

}
