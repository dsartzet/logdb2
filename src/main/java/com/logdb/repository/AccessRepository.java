package com.logdb.repository;

import com.logdb.entity.Access;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccessRepository extends CrudRepository<Access, Long> {
    List<Access> findAll();
}
