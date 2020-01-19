package com.logdb2.document;

import org.springframework.data.mongodb.core.index.Indexed;

public class Dataxceiver extends Log {
    @Indexed
    private Long blockIds;
    private String destinationIps;

    public Long getBlockIds() {
        return blockIds;
    }

    public void setBlockIds(Long blockIds) {
        this.blockIds = blockIds;
    }

    public String getDestinationIps() {
        return destinationIps;
    }

    public void setDestinationIps(String destinationIps) {
        this.destinationIps = destinationIps;
    }
}
