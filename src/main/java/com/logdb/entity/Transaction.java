package com.logdb.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Transaction {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "SOURCE_IP", nullable = false)
    private String sourceIp;

    @OneToMany
    @JoinColumn(name = "DESTINATION_IP", nullable = false)
    private List<DestinationIp> destinantionIps;

    @ManyToMany
    @JoinColumn(name = "BLOCK_ID", nullable = false)
    private List<Block> blocks;

    @Column(name = "TYPE", nullable = false)
    private String type;

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

    public List<DestinationIp> getDestinantionIps() {
        return destinantionIps;
    }

    public void setDestinantionIps(List<DestinationIp> destinantionIps) {
        this.destinantionIps = destinantionIps;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
