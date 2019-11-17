package com.logdb.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NamesystemDto  extends LogDto {

    private String sourceIp;
    private Long size;
    private List<Long> blockIds = new ArrayList<>();
    private List<String> destinationIps = new ArrayList<>();

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<Long> getBlockIds() {
        return blockIds;
    }

    public void setBlockIds(List<Long> blockIds) {
        this.blockIds = blockIds;
    }

    public List<String> getDestinationIps() {
        return destinationIps;
    }

    public void setDestinationIps(List<String> destinationIps) {
        this.destinationIps = destinationIps;
    }

    public void setDestinationIpsWithString(String destinationIps) {
        if(Objects.isNull(destinationIps)){
            destinationIps = null;
        } else {
            this.destinationIps= Arrays.asList(destinationIps.split(","));
        }
    }

    public String getDestinationIpsWithString() {
        if(Objects.isNull(destinationIps)){
            return null;
        } else {
           return destinationIps.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));
        }
    }

    public String getBlockIdsWithString( ) {
        if(Objects.isNull(blockIds)){
            return null;
        } else {
            return blockIds.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));
        }
    }

}
