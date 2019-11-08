package com.logdb.service;

import com.logdb.dto.ClientDto;

public interface ClientService {
    ClientDto findAllById(long id);
    ClientDto save(ClientDto clientDto);
    ClientDto findAllByEmail(String email);
}
