package com.logdb.repository;

import com.logdb.entity.Dataxceiver;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DataxceiverRepository extends CrudRepository<Dataxceiver, Long> {

    List<Dataxceiver> findAll();

}
