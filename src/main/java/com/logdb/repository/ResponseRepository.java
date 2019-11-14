package com.logdb.repository;

import com.logdb.entity.Response;
import org.springframework.data.repository.CrudRepository;

public interface ResponseRepository extends CrudRepository<Response, Long> {
    Response findByStatusAndSize(String status, Long size);
}
