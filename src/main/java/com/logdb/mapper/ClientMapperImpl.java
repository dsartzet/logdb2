package com.logdb.mapper;

import java.util.Objects;

import com.logdb.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logdb.dto.ClientDto;

@Component
public class ClientMapperImpl implements  ClientMapper {

    @Autowired
    protected RoleMapper roleMapper;

    @Override
    public Client convert(ClientDto clientDto) {
        if(Objects.isNull(clientDto)) {
            return null;
        }
        Client client = new Client();
        client.setEmail(clientDto.getEmail());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setId(clientDto.getId());
        client.setPassword(clientDto.getPassword());
        client.setActive(clientDto.isActive());
        client.setRoles(roleMapper.convertDtoCollection(clientDto.getRoles()));
        return client;
    }

    @Override
    public ClientDto convert(Client client) {
        if(Objects.isNull(client)) {
            return null;
        }
        ClientDto clientDto = new ClientDto();
        clientDto.setEmail(client.getEmail());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setId(client.getId());
        clientDto.setPassword(client.getPassword());
        clientDto.setActive(client.isActive());
        clientDto.setRoles(roleMapper.convertEntityCollection(client.getRoles()));
        return clientDto;
    }
}
