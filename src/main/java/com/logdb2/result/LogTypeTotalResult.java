package com.logdb2.result;

import com.logdb2.document.TypeEnum;

public class LogTypeTotalResult {
    private Integer type;
    private long total;

    public String getType() {
        return TypeEnum.getValues()[type].name().toLowerCase();
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
