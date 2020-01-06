package com.logdb2.dto;

import java.util.Date;
import java.util.List;

public class MostCommonLogsIpDateResponseDto {

     private List<LogDto> logs;
     private  String Ip;
     private Date date;

    public List<LogDto> getLogs() {
        return logs;
    }

    public void setLogs(List<LogDto> logs) {
        this.logs = logs;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
