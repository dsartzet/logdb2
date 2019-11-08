package com.logdb.mapper;

import com.logdb.dto.BlockDto;
import com.logdb.entity.Block;

public interface BlockMapper {
    Block convert(BlockDto blockDto);
    BlockDto convert(Block block);
}
