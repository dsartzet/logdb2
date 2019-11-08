package com.logdb.dao;

import com.logdb.entity.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestDao extends CrudRepository<Request, Long> {
}
