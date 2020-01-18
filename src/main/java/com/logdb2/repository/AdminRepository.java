package com.logdb2.repository;

import com.logdb2.document.Admin;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, ObjectId> {
}
