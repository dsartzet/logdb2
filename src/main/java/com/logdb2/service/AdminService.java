package com.logdb2.service;

import com.logdb2.result.MostUpvotesInDifferentIpsResult;
import com.logdb2.result.MostUpvotesResult;
import org.bson.types.ObjectId;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface AdminService {
    void upvote(ObjectId adminId, ObjectId logId) throws ValidationException;

    List<MostUpvotesResult> mostUpvotesGiven();

    List<MostUpvotesInDifferentIpsResult> mostUpvotesInDifferentIps();
}
