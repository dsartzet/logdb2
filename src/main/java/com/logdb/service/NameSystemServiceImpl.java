package com.logdb.service;

import com.logdb.dto.NamesystemDto;
import com.logdb.mapper.NamesystemMapper;
import com.logdb.repository.NamesystemRepository;
import com.logdb.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Component
public class NameSystemServiceImpl implements NameSystemService {


    @Autowired
    private NamesystemRepository namesystemRepository;

    @Autowired
    private NamesystemMapper namesystemMapper;

    @Override
    public Page<NamesystemDto> findAll(int page) {
        return namesystemRepository.findAll((PageRequest.of(Pager.subtractPageByOne(page), 1))).map(nms -> namesystemMapper.convert(nms));
    }

    @Override
    public Page<NamesystemDto> findByIp(int page, String ip) {
        return namesystemRepository.findBySourceIpOrDestinationIps(ip, ip,(PageRequest.of(Pager.subtractPageByOne(page), 1))).map(nms -> namesystemMapper.convert(nms));
    }

    @Override
    public NamesystemDto findById(Long id) {
        return namesystemMapper.convert(namesystemRepository.findById(id).get());
    }

    @Override
    public NamesystemDto insert(NamesystemDto namesystemDto) {
        namesystemDto.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return namesystemMapper.convert(namesystemRepository.save(namesystemMapper.convert(namesystemDto)));
    }

    @Override
    public void delete(NamesystemDto namesystemDto) {
        namesystemRepository.delete(namesystemMapper.convert(namesystemDto));
    }
}
