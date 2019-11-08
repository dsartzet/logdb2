package com.logdb.service;

import com.logdb.dao.AccessDao;
import com.logdb.dto.AccessDto;
import com.logdb.entity.Access;
import com.logdb.mapper.AccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class AccessServiceImpl implements AccessService {

    @Autowired
    protected AccessDao accessDao;

    @Autowired
    protected AccessMapper accessMapper;

    @Override
    public AccessDto findById(Long id) {
        return accessMapper.convert(accessDao.findById(id).get());
    }

    @Override
    public AccessDto insert(AccessDto accessDto) {
        return accessMapper.convert(accessDao.save(accessMapper.convert(accessDto)));
    }

    @Override
    public void delete(AccessDto accessDto) {
        accessDao.delete(accessMapper.convert(accessDto));
    }

    @Override
    public Page<AccessDto> findAll(int page) {
        return accessDao.findAll(new PageRequest(subtractPageByOne(page), 5)).map(access -> accessMapper.convert(access));
    }

    protected int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1;
    }

}
