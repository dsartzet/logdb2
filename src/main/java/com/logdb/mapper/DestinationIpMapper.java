package com.logdb.mapper;

import com.logdb.dto.DestinationIpDto;
import com.logdb.entity.DestinationIp;


public interface DestinationIpMapper {

    DestinationIp convert(DestinationIpDto DestinationIpDto);
    DestinationIpDto convert(DestinationIp DestinationIp);
}
