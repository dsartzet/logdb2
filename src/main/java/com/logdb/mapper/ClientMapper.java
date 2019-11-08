package com.logdb.mapper;

import com.logdb.dto.ClientDto;
import com.logdb.entity.Client;

public interface ClientMapper {
    Client convert(ClientDto clientDto);
    ClientDto convert(Client client);
}
