package com.logdb.repository;

import com.logdb.entity.Dataxceiver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DataxceiverRepository extends CrudRepository<Dataxceiver, Long> {

    Page<Dataxceiver> findAll(Pageable pageRequest);
    Page<Dataxceiver> findBySourceIpOrDestinationIp(String sip, String dip, Pageable pageRequest);

}
