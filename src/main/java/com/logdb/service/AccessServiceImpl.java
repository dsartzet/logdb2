package com.logdb.service;

import com.logdb.repository.AccessRepository;
import com.logdb.dto.AccessDto;
import com.logdb.mapper.AccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccessServiceImpl implements AccessService {

    @Autowired
    protected AccessRepository accessRepository;

    @Autowired
    protected AccessMapper accessMapper;

    @Override
    public AccessDto findById(Long id) {
        return accessMapper.convert(accessRepository.findById(id).get());
    }

    @Override
    public AccessDto insert(AccessDto accessDto) {
        return accessMapper.convert(accessRepository.save(accessMapper.convert(accessDto)));
    }

    @Override
    public void delete(AccessDto accessDto) {
        accessRepository.delete(accessMapper.convert(accessDto));
    }

    @Override
    public List<AccessDto> findAll() {
        return accessRepository.findAll().stream().map(access -> accessMapper.convert(access)).collect(Collectors.toList());
    }

}
