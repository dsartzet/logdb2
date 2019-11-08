package com.logdb.mapper;

import com.logdb.dto.TransactionDto;
import com.logdb.entity.Transaction;

public interface TransactionMapper {
    Transaction convert(TransactionDto transactionDto);
    TransactionDto convert(Transaction transaction);
}
