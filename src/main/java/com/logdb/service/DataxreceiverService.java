package com.logdb.service;

import com.logdb.dto.DataxceiverDto;

import java.util.List;

public interface DataxreceiverService {

    List<DataxceiverDto> findAll();

    DataxceiverDto findById(Long id);

    DataxceiverDto insert(DataxceiverDto dataxceiverDto);

    void delete(DataxceiverDto dataxceiverDto);

}
