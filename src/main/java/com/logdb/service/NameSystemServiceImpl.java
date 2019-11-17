package com.logdb.service;

import com.logdb.dto.NamesystemDto;
import com.logdb.mapper.NamesystemMapper;
import com.logdb.repository.NamesystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NameSystemServiceImpl implements NameSystemService {


    @Autowired
    private NamesystemRepository namesystemRepository;

    @Autowired
    private NamesystemMapper namesystemMapper;

    @Override
    public List<NamesystemDto> findAll() {
        return namesystemRepository.findAll().stream().map(nms -> namesystemMapper.convert(nms)).collect(Collectors.toList());
    }

    @Override
    public NamesystemDto findById(Long id) {
        return namesystemMapper.convert(namesystemRepository.findById(id).get());
    }

    @Override
    public NamesystemDto insert(NamesystemDto namesystemDto) {
        return namesystemMapper.convert(namesystemRepository.save(namesystemMapper.convert(namesystemDto)));
    }

    @Override
    public void delete(NamesystemDto namesystemDto) {
        namesystemRepository.delete(namesystemMapper.convert(namesystemDto));
    }
}
