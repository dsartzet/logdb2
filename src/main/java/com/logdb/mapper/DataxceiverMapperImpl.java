package com.logdb.mapper;

import com.logdb.dto.DataxceiverDto;
import com.logdb.entity.Dataxceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DataxceiverMapperImpl implements DataxceiverMapper {

    @Autowired
    protected BlockMapper blockMapper;

    @Autowired
    protected DestinationIpMapper destinationIpMapper;

    @Override
    public Dataxceiver convert(DataxceiverDto dataxceiverDto) {
        if(Objects.isNull(dataxceiverDto)) {
            return null;
        }
        Dataxceiver dataxceiver = new Dataxceiver();
//        transaction.setBlocks(transactionDto.getBlockDtos().stream().map(blk -> blockMapper.convert(blk)).collect(Collectors.toList()));
        dataxceiver.setId(dataxceiverDto.getId());
//        transaction.setDestinantionIps(transactionDto.getDestinationIps().stream().map(dips -> destinationIpMapper.convert(dips)).collect(Collectors.toList()));
        dataxceiver.setSourceIp(dataxceiverDto.getSourceIp());
        dataxceiver.setType(dataxceiverDto.getType());
        return dataxceiver;
    }

    @Override
    public DataxceiverDto convert(Dataxceiver dataxceiver) {
        if(Objects.isNull(dataxceiver)) {
            return null;
        }
        DataxceiverDto dataxceiverDto = new DataxceiverDto();
//        transactionDto.setBlockDtos(transaction.getBlocks().stream().map(blk -> blockMapper.convert(blk)).collect(Collectors.toList()));
        dataxceiverDto.setId(dataxceiver.getId());
//        transactionDto.setDestinationIps(transaction.getDestinantionIps().stream().map(dips -> destinationIpMapper.convert(dips)).collect(Collectors.toList()));
        dataxceiverDto.setSourceIp(dataxceiver.getSourceIp());
        dataxceiverDto.setType(dataxceiver.getType());
        return dataxceiverDto;
    }
}
