package com.logdb2.service;

import com.logdb2.document.Admin;
import com.logdb2.document.Log;
import com.logdb2.document.Upvote;
import com.logdb2.document.Upvoter;
import com.logdb2.repository.AdminRepository;
import com.logdb2.repository.LogRepository;
import com.logdb2.result.MostUpvotesInDifferentIpsResult;
import com.logdb2.result.MostUpvotesResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class AdminServiceImpl implements AdminService {

    private static final String ADMIN_COLLECTION_NAME = "admin";
    private static final AggregationOptions AGGREGATION_OPTIONS = Aggregation.newAggregationOptions()
            .allowDiskUse(true).build();

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void upvote(ObjectId adminId, ObjectId logId) throws ValidationException {
        Optional<Admin> adminOptional= adminRepository.findById(adminId);
        if(adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            List<Upvote> upvotes = admin.getUpvotes() != null ? admin.getUpvotes() : new ArrayList<>();
            if (upvotes.stream().map(Upvote::getLog).collect(Collectors.toList()).contains(logId)) {
                throw new ValidationException("Can not upvote same log multiple times.");
            }
            Log log = logRepository.findById(logId).get();
            upvotes.add(new Upvote(logId, log.getSourceIp()));
            if (log.getUpvoters() == null) log.setUpvoters(new ArrayList<>());
            log.getUpvoters().add(new Upvoter(admin.getUsername(), admin.getEmail()));
            admin.setUpvotes(upvotes);
            adminRepository.save(admin);
            logRepository.save(log);
        }
    }

    @Override
    public List<MostUpvotesResult> mostUpvotesGiven() {
        Aggregation agg = newAggregation(
                unwind("upvotes"),
                group("_id")
                    .first("username").as("username")
                    .first("email").as("email")
                    .first("phoneNumber").as("phoneNumber")
                    .push("upvotes.log").as("upvotes")
                    .count().as("size"),
                sort(Sort.Direction.DESC, "size"),
                limit(50))
                .withOptions(AGGREGATION_OPTIONS);
        AggregationResults<MostUpvotesResult> groupResults = mongoTemplate.aggregate(agg, ADMIN_COLLECTION_NAME, MostUpvotesResult.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<MostUpvotesInDifferentIpsResult> mostUpvotesInDifferentIps() {
        Aggregation agg = newAggregation(
                unwind("upvotes"),
                group("_id")
                        .addToSet("upvotes.sourceIp").as("data"),
                        project("_id").and(ArrayOperators.Size.lengthOfArray(ConditionalOperators.ifNull("$data").then(Collections.emptyList()))).as("set_size"),
                sort(Sort.Direction.DESC, "set_size"),
                limit(50))
                .withOptions(AGGREGATION_OPTIONS);
        AggregationResults<MostUpvotesInDifferentIpsResult> groupResults = mongoTemplate.aggregate(agg, ADMIN_COLLECTION_NAME, MostUpvotesInDifferentIpsResult.class);
        return groupResults.getMappedResults();
    }
}
