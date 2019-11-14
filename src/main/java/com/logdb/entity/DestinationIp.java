package com.logdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DestinationIp {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Column(name = "DESTINATION_IP", unique = true, nullable = false)
    private String destinationIp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp;
    }
}
