package com.logdb.service;

import com.logdb.dto.DataxceiverDto;
import com.logdb.mapper.DataxceiverMapper;
import com.logdb.repository.DataxceiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataxreceiverServiceImpl implements DataxreceiverService {

    @Autowired
    private DataxceiverRepository dataxceiverRepository;

    @Autowired
    private DataxceiverMapper dataxceiverMapper;

    @Override
    public List<DataxceiverDto> findAll() {
        return dataxceiverRepository.findAll().stream().map(dxc -> dataxceiverMapper.convert(dxc)).collect(Collectors.toList());
    }

    @Override
    public DataxceiverDto findById(Long id) {
        return dataxceiverMapper.convert(dataxceiverRepository.findById(id).get());
    }

    @Override
    public DataxceiverDto insert(DataxceiverDto dataxceiverDto) {
        return dataxceiverMapper.convert(dataxceiverRepository.save(dataxceiverMapper.convert(dataxceiverDto)));
    }

    @Override
    public void delete(DataxceiverDto dataxceiverDto) {
        dataxceiverRepository.delete(dataxceiverMapper.convert(dataxceiverDto));
    }
}
