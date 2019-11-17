package com.logdb.mapper;

import com.logdb.dto.DataxceiverDto;
import com.logdb.entity.Dataxceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DataxceiverMapperImpl implements DataxceiverMapper {

    @Override
    public Dataxceiver convert(DataxceiverDto dataxceiverDto) {
        if(Objects.isNull(dataxceiverDto)) {
            return null;
        }
        Dataxceiver dataxceiver = new Dataxceiver();
        dataxceiver.setId(dataxceiverDto.getId());
        dataxceiver.setSourceIp(dataxceiverDto.getSourceIp());
        dataxceiver.setType(dataxceiverDto.getType());
        dataxceiver.setBlockId(dataxceiverDto.getBlockId());
        dataxceiver.setDestinationIp(dataxceiverDto.getDestinationIp());
        dataxceiver.setSize(dataxceiverDto.getSize());
        dataxceiver.setTimestamp(dataxceiverDto.getTimestamp());
        return dataxceiver;
    }

    @Override
    public DataxceiverDto convert(Dataxceiver dataxceiver) {
        if(Objects.isNull(dataxceiver)) {
            return null;
        }
        DataxceiverDto dataxceiverDto = new DataxceiverDto();
        dataxceiverDto.setId(dataxceiver.getId());
        dataxceiverDto.setSourceIp(dataxceiver.getSourceIp());
        dataxceiverDto.setType(dataxceiver.getType());
        dataxceiverDto.setBlockId(dataxceiver.getBlockId());
        dataxceiverDto.setDestinationIp(dataxceiver.getDestinationIp());
        dataxceiverDto.setSize(dataxceiver.getSize());
        dataxceiverDto.setTimestamp(dataxceiver.getTimestamp());
        return dataxceiverDto;
    }
}
