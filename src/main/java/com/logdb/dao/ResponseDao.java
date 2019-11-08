package com.logdb.dao;

import com.logdb.entity.Response;
import org.springframework.data.repository.CrudRepository;

public interface ResponseDao extends CrudRepository<Response, Long> {
}
