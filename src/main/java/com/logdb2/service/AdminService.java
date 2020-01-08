package com.logdb2.service;

import org.bson.types.ObjectId;

import javax.xml.bind.ValidationException;

public interface AdminService {
    void upvote(long adminId, ObjectId logId) throws ValidationException;
}
