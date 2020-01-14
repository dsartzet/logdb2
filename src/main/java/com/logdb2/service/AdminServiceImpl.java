package com.logdb2.service;

import com.logdb2.document.Admin;
import com.logdb2.document.Upvote;
import com.logdb2.repository.AdminRepository;
import com.logdb2.repository.LogRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    LogRepository logRepository;

    @Override
    public void upvote(long adminId, ObjectId logId) throws ValidationException {
        Optional<Admin> adminOptional= adminRepository.findById(adminId);
        if(adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            List<Upvote> upvotes = admin.getUpvotes();
            if (upvotes.stream().map(Upvote::getLog).collect(Collectors.toList()).contains(logId)) {
                throw new ValidationException("Can not upvote same log multiple times.");
            }
            upvotes.add(new Upvote(logId, logRepository.findById(logId).get().getSourceIp()));
            admin.setUpvotes(upvotes);
            adminRepository.save(admin);
        }
    }
}
