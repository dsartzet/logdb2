package com.logdb2.result;

import java.util.List;

public class BlocksSameDayReplicateAndServedResult {
    private Long blockIds;
    private Integer year;
    private Integer month;
    private Integer day;
    private List<Integer> types;

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

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }
}
