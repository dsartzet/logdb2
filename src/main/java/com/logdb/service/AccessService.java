package com.logdb.service;

import com.logdb.dto.AccessDto;
import com.logdb.dto.LogDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccessService {

    AccessDto findById(Long id);

    AccessDto insert(AccessDto accessDto);

    void delete(AccessDto accessDto);

    List<AccessDto> findAll();
}
