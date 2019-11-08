package com.logdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DestinationIp {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Column(name = "IP", unique = true, nullable = false)
    private String ip;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
