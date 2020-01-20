package com.logdb2.result;

import java.util.List;

public class BlocksSameDayReplicateAndServedResult {
    private List<String> types;
    private Long blockIds;
    private Integer year;
    private Integer month;
    private Integer day;

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Long getBlockIds() {
        return blockIds;
    }

    public void setBlockIds(Long blockIds) {
        this.blockIds = blockIds;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
