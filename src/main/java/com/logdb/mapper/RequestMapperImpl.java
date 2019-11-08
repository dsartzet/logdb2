package com.logdb.mapper;

import com.logdb.dto.RequestDto;
import com.logdb.entity.Request;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RequestMapperImpl implements RequestMapper {
    @Override
    public Request convert(RequestDto requestDto) {
        if(Objects.isNull(requestDto)) {
            return null;
        }
        Request request = new Request();
        request.setId(requestDto.getId());
        request.setMethod(requestDto.getMethod());
        request.setResource(requestDto.getResource());
        return request;
    }

    @Override
    public RequestDto convert(Request request) {
        if(Objects.isNull(request)) {
            return null;
        }
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setMethod(request.getMethod());
        requestDto.setResource(request.getResource());
        return requestDto;
    }
}
