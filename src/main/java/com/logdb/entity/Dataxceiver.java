package com.logdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class Dataxceiver {

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

    @ManyToOne
    @JoinColumn(name = "BLOCK_ID", nullable = false)
    private Block block;

    @ManyToOne
    @JoinColumn(name = "DESTINATION_IP_ID", nullable = false)
    private DestinationIp destinationIp;

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

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public DestinationIp getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(DestinationIp destinationIp) {
        this.destinationIp = destinationIp;
    }
}
