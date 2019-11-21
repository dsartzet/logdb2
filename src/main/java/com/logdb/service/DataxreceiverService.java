package com.logdb.service;

import com.logdb.dto.DataxceiverDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DataxreceiverService {

    Page<DataxceiverDto> findAll(int page);

    DataxceiverDto findById(Long id);

    DataxceiverDto insert(DataxceiverDto dataxceiverDto);

    void delete(DataxceiverDto dataxceiverDto);

    Page<DataxceiverDto> findByIp(int page, String ip);

}
