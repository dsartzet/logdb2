package com.logdb2.result;

import java.time.LocalDateTime;

public class LogDateTotalResult {
    private LocalDateTime timestamp;
    private long total;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
