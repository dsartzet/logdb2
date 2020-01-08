package com.logdb2.dto;

public class LogTypeCounterPairResponseDto {
    private String type;
    private long numberOfLogs;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getNumberOfLogs() {
        return numberOfLogs;
    }

    public void setNumberOfLogs(long numberOfLogs) {
        this.numberOfLogs = numberOfLogs;
    }
}
