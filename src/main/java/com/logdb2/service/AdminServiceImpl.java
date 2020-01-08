package com.logdb2.service;

import com.logdb2.document.Admin;
import com.logdb2.repository.AdminRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public void upvote(long adminId, ObjectId logId) throws ValidationException {
        Optional<Admin> adminOptional= adminRepository.findById(adminId);
        if(adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            List<ObjectId> upvotes = admin.getUpvotes();
            if (upvotes.contains(logId)) {
                throw new ValidationException("Can not upvote same log multiple times.");
            }
            upvotes.add(logId);
            admin.setUpvotes(upvotes);
            adminRepository.save(admin);
        }
    }
}
