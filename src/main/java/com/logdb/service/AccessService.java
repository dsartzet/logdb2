package com.logdb.service;

import com.logdb.dto.AccessDto;
import org.springframework.data.domain.Page;

public interface AccessService {

    AccessDto findById(Long id);

    AccessDto insert(AccessDto accessDto);

    void delete(AccessDto accessDto);

    Page<AccessDto> findAll(int page);
}
