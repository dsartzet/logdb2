package com.logdb.mapper;

import com.logdb.dto.DataxceiverDto;
import com.logdb.entity.Dataxceiver;

public interface DataxceiverMapper {
    Dataxceiver convert(DataxceiverDto dataxceiverDto);
    DataxceiverDto convert(Dataxceiver dataxceiver);
}
