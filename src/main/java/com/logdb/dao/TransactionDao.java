package com.logdb.dao;

import com.logdb.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionDao extends CrudRepository<Transaction, Long> {
}
