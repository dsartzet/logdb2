package com.logdb.dto;

import java.util.List;

public class TransactionDto {

    private Long id;
    private String sourceIp;
    private List<DestinationIpDto> destinationIps;
    private List<BlockDto> blockDtos;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public List<DestinationIpDto> getDestinationIps() {
        return destinationIps;
    }

    public void setDestinationIps(List<DestinationIpDto> destinationIps) {
        this.destinationIps = destinationIps;
    }

    public List<BlockDto> getBlockDtos() {
        return blockDtos;
    }

    public void setBlockDtos(List<BlockDto> blockDtos) {
        this.blockDtos = blockDtos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
