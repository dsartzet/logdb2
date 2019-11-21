package com.logdb.repository;

import com.logdb.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventLogRepository extends CrudRepository<Event, Long> {
}
