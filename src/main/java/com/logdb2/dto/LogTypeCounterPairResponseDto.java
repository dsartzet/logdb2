package com.logdb2.dto;

public class LogTypeCounterPairResponseDto {
    private String type;
    private Integer numberOfLogs;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumberOfLogs() {
        return numberOfLogs;
    }

    public void setNumberOfLogs(Integer numberOfLogs) {
        this.numberOfLogs = numberOfLogs;
    }
}
