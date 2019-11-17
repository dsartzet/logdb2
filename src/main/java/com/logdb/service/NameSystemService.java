package com.logdb.service;

import com.logdb.dto.NamesystemDto;

import java.util.List;

public interface NameSystemService {


    NamesystemDto findById(Long id);

    NamesystemDto insert(NamesystemDto namesystemDto);

    void delete(NamesystemDto namesystemDto);


    List<NamesystemDto> findAll();

}
