package com.logdb.repository;

import com.logdb.entity.Namesystem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NamesystemRepository extends CrudRepository<Namesystem, Long> {

    List<Namesystem> findAll();
}
