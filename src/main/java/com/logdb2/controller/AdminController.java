package com.logdb2.controller;

import com.logdb2.service.AdminService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.bind.ValidationException;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/admin/{adminId}/upvote/{logId}", method = RequestMethod.POST)
    void upvote(@PathVariable String logId, @PathVariable long adminId) throws ValidationException {
        adminService.upvote(adminId,  new ObjectId(logId));
    }
}
