package com.logdb.mapper;

import com.logdb.dto.NamesystemDto;
import com.logdb.entity.Namesystem;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NamesystemMapperImpl implements  NamesystemMapper{
    @Override
    public Namesystem convert(NamesystemDto namesystemDto) {
        if(Objects.isNull(namesystemDto)) {
            return null;
        }
        Namesystem namesystem = new Namesystem();
        namesystem.setBlockIds(namesystemDto.getBlockIds());
        namesystem.setDestinationIps(namesystemDto.getDestinationIps());
        namesystem.setId(namesystemDto.getId());
        namesystem.setSize(namesystemDto.getSize());
        namesystem.setSourceIp(namesystemDto.getSourceIp());
        namesystem.setTimestamp(namesystemDto.getTimestamp());
        namesystem.setType(namesystemDto.getType());
        return namesystem;
    }

    @Override
    public NamesystemDto convert(Namesystem namesystem) {
        if(Objects.isNull(namesystem)) {
            return null;
        }
        NamesystemDto namesystemDto = new NamesystemDto();
        namesystemDto.setBlockIds(namesystem.getBlockIds());
        namesystemDto.setDestinationIps(namesystem.getDestinationIps());
        namesystemDto.setId(namesystem.getId());
        namesystemDto.setSize(namesystem.getSize());
        namesystemDto.setSourceIp(namesystem.getSourceIp());
        namesystemDto.setTimestamp(namesystem.getTimestamp());
        namesystemDto.setType(namesystem.getType());
        return namesystemDto;
    }
}
