package com.logdb.mapper;

import com.logdb.dto.RequestDto;
import com.logdb.entity.Request;

public interface RequestMapper {
    Request convert(RequestDto requestDto);
    RequestDto convert(Request request);
}
