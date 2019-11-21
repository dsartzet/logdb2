package com.logdb.service;

import com.logdb.entity.Event;

public interface EventLogService {
    void save(Event event);
}
