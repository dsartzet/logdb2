package com.logdb.repository;

import com.logdb.entity.Namesystem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface NamesystemRepository extends CrudRepository<Namesystem, Long> {

    Page<Namesystem> findAll(Pageable pageRequest);

    Page<Namesystem> findBySourceIpOrDestinationIps(String sip, String dip, Pageable pageRequest);


}
