package com.logdb.repository;

import com.logdb.entity.Block;
import org.springframework.data.repository.CrudRepository;

public interface BlockRepository extends CrudRepository<Block, Long> {
}
