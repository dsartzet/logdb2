package com.logdb.repository;

import com.logdb.entity.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
    Session findBySourceIpAndUserAgent(String sourceIp, String userAgent);
}
