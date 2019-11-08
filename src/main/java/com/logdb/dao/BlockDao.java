package com.logdb.dao;

import com.logdb.entity.Block;
import org.springframework.data.repository.CrudRepository;

public interface BlockDao extends CrudRepository<Block, Long> {
}
