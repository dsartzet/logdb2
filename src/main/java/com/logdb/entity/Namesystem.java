package com.logdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

    @ManyToMany
    @JoinTable(name = "namesystem_block",
            joinColumns = @JoinColumn(name = "namesystem_id"),
            inverseJoinColumns = @JoinColumn(name = "block_id")
    )
    private List<Block> blockList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "namesystem_destinationIp",
            joinColumns = @JoinColumn(name = "namesystem_id"),
            inverseJoinColumns = @JoinColumn(name = "destinationIp_id")
    )
    private List<DestinationIp> destinationIpList = new ArrayList<>();

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

    public List<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<Block> blockList) {
        this.blockList = blockList;
    }

    public List<DestinationIp> getDestinationIpList() {
        return destinationIpList;
    }

    public void setDestinationIpList(List<DestinationIp> destinationIpList) {
        this.destinationIpList = destinationIpList;
    }
}
