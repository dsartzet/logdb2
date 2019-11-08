package com.logdb.mapper;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.logdb.dto.RoleDto;
import com.logdb.entity.Role;

@Component
public class RoleMapperImpl implements RoleMapper {
    @Override
    public RoleDto convert(Role role) {
        if(Objects.isNull(role)) {
            return null;
        }
        RoleDto roleDto =  new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setRole(role.getRole());
        return roleDto;
    }

    @Override
    public Role convert(RoleDto roleDto) {
        if(Objects.isNull(roleDto)) {
            return null;
        }
        Role role =  new Role();
        role.setId(roleDto.getId());
        role.setRole(roleDto.getRole());
        return role;    }

    @Override
    public Set<Role> convertDtoCollection(Set<RoleDto> roleDtos) {
        return Objects.isNull(roleDtos) ? null : roleDtos.stream().map(this::convert).collect(Collectors.toSet());
    }

    @Override
    public Set<RoleDto> convertEntityCollection(Set<Role> roles) {
        return Objects.isNull(roles) ? null : roles.stream().map(this::convert).collect(Collectors.toSet());
    }
}
