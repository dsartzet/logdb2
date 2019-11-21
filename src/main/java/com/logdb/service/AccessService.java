package com.logdb.service;

import com.logdb.dto.AccessDto;
import com.logdb.dto.LogDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccessService {

    AccessDto findById(Long id);

    AccessDto insert(AccessDto accessDto);

    void delete(AccessDto accessDto);

    Page<AccessDto> findAll(int page);

    Page<AccessDto> findByIp(int page, String ip);
}
