package com.logdb.mapper;

import com.logdb.dto.TransactionDto;
import com.logdb.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Autowired
    protected BlockMapper blockMapper;

    @Autowired
    protected DestinationIpMapper destinationIpMapper;

    @Override
    public Transaction convert(TransactionDto transactionDto) {
        if(Objects.isNull(transactionDto)) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setBlocks(transactionDto.getBlockDtos().stream().map(blk -> blockMapper.convert(blk)).collect(Collectors.toList()));
        transaction.setId(transactionDto.getId());
        transaction.setDestinantionIps(transactionDto.getDestinationIps().stream().map(dips -> destinationIpMapper.convert(dips)).collect(Collectors.toList()));
        transaction.setSourceIp(transactionDto.getSourceIp());
        transaction.setType(transactionDto.getType());
        return transaction;
    }

    @Override
    public TransactionDto convert(Transaction transaction) {
        if(Objects.isNull(transaction)) {
            return null;
        }
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setBlockDtos(transaction.getBlocks().stream().map(blk -> blockMapper.convert(blk)).collect(Collectors.toList()));
        transactionDto.setId(transaction.getId());
        transactionDto.setDestinationIps(transaction.getDestinantionIps().stream().map(dips -> destinationIpMapper.convert(dips)).collect(Collectors.toList()));
        transactionDto.setSourceIp(transaction.getSourceIp());
        transactionDto.setType(transaction.getType());
        return transactionDto;
    }
}
