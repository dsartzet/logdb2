package com.logdb.service;

import com.logdb.mapper.RequestMapper;
import com.logdb.mapper.ResponseMapper;
import com.logdb.mapper.SessionMapper;
import com.logdb.repository.AccessRepository;
import com.logdb.dto.AccessDto;
import com.logdb.mapper.AccessMapper;
import com.logdb.repository.RequestRepository;
import com.logdb.repository.ResponseRepository;
import com.logdb.repository.SessionRepository;
import com.logdb.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccessServiceImpl implements AccessService {

    @Autowired
    protected AccessRepository accessRepository;
    @Autowired
    protected RequestRepository requestRepository;
    @Autowired
    protected ResponseRepository responseRepository;
    @Autowired
    protected SessionRepository sessionRepository;
    @Autowired
    protected AccessMapper accessMapper;
    @Autowired
    protected RequestMapper requestMapper;
    @Autowired
    protected ResponseMapper responseMapper;
    @Autowired
    protected SessionMapper sessionMapper;

    @Override
    public AccessDto findById(Long id) {
        return accessMapper.convert(accessRepository.findById(id).get());
    }

    @Override
    public AccessDto insert(AccessDto accessDto) {
        accessDto.setTimestamp(new Timestamp(System.currentTimeMillis()));
        accessDto.setSessionDto(sessionMapper.convert(sessionRepository.save(sessionMapper.convert(accessDto.getSessionDto()))));
        accessDto.setResponseDto(responseMapper.convert(responseRepository.save(responseMapper.convert(accessDto.getResponseDto()))));
        accessDto.setRequestDto(requestMapper.convert(requestRepository.save(requestMapper.convert(accessDto.getRequestDto()))));
        return accessMapper.convert(accessRepository.save(accessMapper.convert(accessDto)));
    }

    @Override
    public void delete(AccessDto accessDto) {
        accessRepository.delete(accessMapper.convert(accessDto));
    }

    @Override
    public Page<AccessDto> findAll(int page) {
        return accessRepository.findAll(PageRequest.of(Pager.subtractPageByOne(page), 1)).map(access -> accessMapper.convert(access));
    }

    @Override
    public Page<AccessDto> findByIp(int page, String ip) {
        return accessRepository.findBySessionSourceIp(ip, PageRequest.of(Pager.subtractPageByOne(page), 1)).map(access -> accessMapper.convert(access));
    }
}
