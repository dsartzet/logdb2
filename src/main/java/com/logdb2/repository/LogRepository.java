package com.logdb2.repository;

import com.logdb2.document.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, ObjectId> {
}
