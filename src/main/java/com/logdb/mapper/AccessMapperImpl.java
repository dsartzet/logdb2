package com.logdb.mapper;

import com.logdb.dto.AccessDto;
import com.logdb.entity.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccessMapperImpl implements AccessMapper{

    @Autowired
    protected ResponseMapper responseMapper;

    @Autowired
    protected RequestMapper requestMapper;


    @Autowired
    protected SessionMapper sessionMapper;

    @Override
    public Access convert(AccessDto accessDto) {
        if (Objects.isNull(accessDto)) {
            return null;
        }
        Access access = new Access();
        access.setId(accessDto.getId());
        access.setRequest(requestMapper.convert(accessDto.getRequestDto()));
        access.setResponse(responseMapper.convert(accessDto.getResponseDto()));
        access.setSession(sessionMapper.convert(accessDto.getSessionDto()));
        access.setTimestamp(accessDto.getTimestamp());
        access.setReferer(accessDto.getReferer());
        return access;
    }

    @Override
    public AccessDto convert(Access access) {
        if (Objects.isNull(access)) {
            return null;
        }
        AccessDto accessDto = new AccessDto();
        accessDto.setId(accessDto.getId());
        accessDto.setRequestDto(requestMapper.convert(access.getRequest()));
        accessDto.setResponseDto(responseMapper.convert(access.getResponse()));
        accessDto.setSessionDto(sessionMapper.convert(access.getSession()));
        accessDto.setTimestamp(access.getTimestamp());
        accessDto.setReferer(access.getReferer());
        return accessDto;
    }
}
