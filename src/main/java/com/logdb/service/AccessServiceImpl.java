package com.logdb.service;

import com.logdb.repository.AccessRepository;
import com.logdb.dto.AccessDto;
import com.logdb.mapper.AccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

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
    public Page<AccessDto> findAll(int page) {
        return accessRepository.findAll(new PageRequest(subtractPageByOne(page), 5)).map(access -> accessMapper.convert(access));
    }

    protected int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1;
    }

}
