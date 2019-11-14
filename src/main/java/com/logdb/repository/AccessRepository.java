package com.logdb.repository;

import com.logdb.entity.Access;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface AccessRepository extends CrudRepository<Access, Long> {
    Page<Access> findAll(Pageable pageRequest);
}
