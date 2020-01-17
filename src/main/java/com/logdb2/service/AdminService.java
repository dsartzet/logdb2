package com.logdb2.service;

import com.logdb2.document.Admin;
import org.bson.types.ObjectId;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface AdminService {
    void upvote(long adminId, ObjectId logId) throws ValidationException;

    List<Admin> mostUpvotesGiven();

    List<Admin> mostUpvotesInDifferentIps();
}
