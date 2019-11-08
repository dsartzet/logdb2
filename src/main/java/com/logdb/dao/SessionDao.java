package com.logdb.dao;

import com.logdb.entity.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionDao extends CrudRepository<Session, Long> {
}
