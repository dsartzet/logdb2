package com.logdb.mapper;

import com.logdb.dto.SessionDto;
import com.logdb.entity.Session;

public interface SessionMapper {
    Session convert(SessionDto sessionDto);
    SessionDto convert(Session session);
}
