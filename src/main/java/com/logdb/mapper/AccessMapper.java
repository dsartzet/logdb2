package com.logdb.mapper;

import com.logdb.dto.AccessDto;
import com.logdb.entity.Access;

public interface AccessMapper {
    Access convert(AccessDto accessDto);
    AccessDto convert(Access access);
}
