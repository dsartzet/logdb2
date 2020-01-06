package com.logdb2.service;

import com.logdb2.dto.ClientDto;

public interface ClientService {
    ClientDto findAllById(long id);
    ClientDto save(ClientDto clientDto);
    ClientDto findAllByEmail(String email);
}
