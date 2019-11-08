package com.logdb.mapper;

import java.util.Set;

import com.logdb.dto.RoleDto;
import com.logdb.entity.Role;

public interface RoleMapper {
    RoleDto convert(Role role);
    Role convert(RoleDto reactionDto);
    Set<Role> convertDtoCollection(Set<RoleDto> roleDtos);
    Set<RoleDto> convertEntityCollection(Set<Role> roles);
}
