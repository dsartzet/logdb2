package com.logdb.repository;

import com.logdb.entity.Access;
import com.logdb.entity.Dataxceiver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccessRepository extends CrudRepository<Access, Long> {
    Page<Access> findAll(Pageable pageRequest);

    Page<Access> findBySessionSourceIp(String sip, Pageable pageRequest);

}
