package com.logdb2.result;

import com.logdb2.document.TypeEnum;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> getTypes() {
        return types.stream()
                .map(type -> TypeEnum.getValues()[type].name().toLowerCase())
                .collect(Collectors.toList());
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }
}
