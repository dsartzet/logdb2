package com.logdb.repository;

import com.logdb.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findAllById(long clientId);
    Client findAllByEmail(String email);

}
