package com.logdb.mapper;

import com.logdb.dto.NamesystemDto;
import com.logdb.entity.Namesystem;

public interface NamesystemMapper {

    Namesystem convert(NamesystemDto namesystemDto);
    NamesystemDto convert(Namesystem namesystem);
}
