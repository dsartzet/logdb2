package com.logdb.service;

import com.logdb.entity.Event;
import com.logdb.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EventLogServiceImpl implements EventLogService {
    @Autowired
    EventLogRepository eventLogRepository;

    @Override
    public void save(Event event) {
        eventLogRepository.save(event);
    }
}
