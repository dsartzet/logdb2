package com.logdb.document;

import java.util.ArrayList;
import java.util.List;

public class Namesystem extends Log {
    private List<Long> blockIds = new ArrayList<>();
    private List<String> destinationIps = new ArrayList<>();

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
}
