package com.logdb.repository;

import com.logdb.entity.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Long> {
    Request findByHttpMethodAndResource(String httpMethod, String resource);
}
