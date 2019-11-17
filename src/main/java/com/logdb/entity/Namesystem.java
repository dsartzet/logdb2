package com.logdb.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Namesystem {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "SOURCE_IP", nullable = false)
    private String sourceIp;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "TIMESTAMP", nullable = false)
    private Timestamp timestamp;

    @Column(name = "SIZE")
    private Long size;

    @ElementCollection
    @CollectionTable(
            name="BLOCK",
            joinColumns=@JoinColumn(name="NAMESYSTEM_ID")
    )
    @Column(name="BLOCK_ID")
    private List<Long> blockIds = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name="DESTINATION_IP",
            joinColumns=@JoinColumn(name="NAMESYSTEM_ID")
    )
    @Column(name="DESTINATION_IP")
    private List<String> destinationIps = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<Long> getBlockIds() {
        return blockIds;
    }

    public void setBlockIds(List<Long> blockIds) {
        this.blockIds = blockIds;
    }

    public List<String> getDestinationIps() {
        return destinationIps;
    }

    public void setDestinationIps(List<String> destinationIps) {
        this.destinationIps = destinationIps;
    }
}
