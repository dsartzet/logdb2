package com.logdb.dao;

import com.logdb.entity.Access;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface AccessDao extends CrudRepository<Access, Long> {
    Page<Access> findAll(Pageable pageRequest);
}
