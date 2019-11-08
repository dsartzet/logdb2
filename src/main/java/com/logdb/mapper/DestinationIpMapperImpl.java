package com.logdb.mapper;

import com.logdb.dto.DestinationIpDto;
import com.logdb.entity.DestinationIp;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DestinationIpMapperImpl implements DestinationIpMapper {
    @Override
    public DestinationIp convert(DestinationIpDto destinationIpDto) {
        if(Objects.isNull(destinationIpDto)) {
            return  null;
        }
        DestinationIp destinationIp = new DestinationIp();
        destinationIp.setId(destinationIpDto.getId());
        destinationIp.setIp(destinationIpDto.getIp());
        return destinationIp;
    }

    @Override
    public DestinationIpDto convert(DestinationIp destinationIp) {
        if(Objects.isNull(destinationIp)) {
            return  null;
        }
        DestinationIpDto destinationIpDto = new DestinationIpDto();
        destinationIpDto.setId(destinationIp.getId());
        destinationIpDto.setIp(destinationIp.getIp());
        return destinationIpDto;
    }
}
