package com.logdb.mapper;

import com.logdb.dto.ResponseDto;
import com.logdb.entity.Response;

public interface ResponseMapper {
    Response convert(ResponseDto responseDto);
    ResponseDto convert(Response response);
}
