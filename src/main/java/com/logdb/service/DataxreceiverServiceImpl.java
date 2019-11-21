package com.logdb.service;

import com.logdb.dto.DataxceiverDto;
import com.logdb.mapper.DataxceiverMapper;
import com.logdb.repository.DataxceiverRepository;
import com.logdb.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataxreceiverServiceImpl implements DataxreceiverService {

    @Autowired
    private DataxceiverRepository dataxceiverRepository;

    @Autowired
    private DataxceiverMapper dataxceiverMapper;

    @Override
    public Page<DataxceiverDto> findAll(int page) {
        return dataxceiverRepository.findAll((PageRequest.of(Pager.subtractPageByOne(page), 1))).map(dxc -> dataxceiverMapper.convert(dxc));
    }

    @Override
    public Page<DataxceiverDto> findByIp(int page, String ip) {
        return dataxceiverRepository.findBySourceIpOrDestinationIp(ip, ip,(PageRequest.of(Pager.subtractPageByOne(page), 1))).map(dxc -> dataxceiverMapper.convert(dxc));
    }


    @Override
    public DataxceiverDto findById(Long id) {
        return dataxceiverMapper.convert(dataxceiverRepository.findById(id).get());
    }

    @Override
    public DataxceiverDto insert(DataxceiverDto dataxceiverDto) {
        dataxceiverDto.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return dataxceiverMapper.convert(dataxceiverRepository.save(dataxceiverMapper.convert(dataxceiverDto)));
    }

    @Override
    public void delete(DataxceiverDto dataxceiverDto) {
        dataxceiverRepository.delete(dataxceiverMapper.convert(dataxceiverDto));
    }
}
