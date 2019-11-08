package com.logdb.dao;

import com.logdb.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientDao extends CrudRepository<Client, Long> {

    Client findAllById(long clientId);
    Client findAllByEmail(String email);

}
