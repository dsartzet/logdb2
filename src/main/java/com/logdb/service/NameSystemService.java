package com.logdb.service;

import com.logdb.dto.NamesystemDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NameSystemService {


    NamesystemDto findById(Long id);

    NamesystemDto insert(NamesystemDto namesystemDto);

    void delete(NamesystemDto namesystemDto);

    Page<NamesystemDto> findByIp(int page, String ip);

    Page<NamesystemDto> findAll(int page);

}
