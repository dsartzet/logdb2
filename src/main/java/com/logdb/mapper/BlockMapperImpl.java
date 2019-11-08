package com.logdb.mapper;

import com.logdb.dto.BlockDto;
import com.logdb.entity.Block;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BlockMapperImpl implements BlockMapper {
    @Override
    public Block convert(BlockDto blockDto) {
        if(Objects.isNull(blockDto)) {
            return  null;
        }
        Block block = new Block();
        block.setId(blockDto.getId());
        block.setSize(blockDto.getSize());
        return block;
    }

    @Override
    public BlockDto convert(Block block) {
        if(Objects.isNull(block)) {
            return  null;
        }
        BlockDto blockDto = new BlockDto();
        blockDto.setId(block.getId());
        blockDto.setSize(block.getSize());
        return blockDto;
    }
}
