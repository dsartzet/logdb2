package com.logdb.mapper;

import com.logdb.dto.ResponseDto;
import com.logdb.entity.Response;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class ResponseMapperImpl implements ResponseMapper {
    @Override
    public Response convert(ResponseDto responseDto) {
        if(Objects.isNull(responseDto)) {
            return null;
        }
        Response response = new Response();
        response.setId(responseDto.getId());
        response.setSize(responseDto.getSize());
//        response.setStatus(responseDto.getStatus());
        return response;
    }

    @Override
    public ResponseDto convert(Response response) {
        if(Objects.isNull(response)) {
            return null;
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setId(response.getId());
        responseDto.setSize(response.getSize());
//        responseDto.setStatus(response.getStatus());
        return responseDto;
    }
}
