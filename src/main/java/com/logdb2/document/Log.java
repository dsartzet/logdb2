package com.logdb2.document;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "logs")
public class Log {
    @Id
    private ObjectId _id;

    @Indexed
    private LocalDateTime timestamp;
    @Indexed
    private String type;
    private Long size;
    private String sourceIp;
    private List<Upvoter> upvoters;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public List<Upvoter> getUpvoters() {
        return upvoters;
    }

    public void setUpvoters(List<Upvoter> upvoters) {
        this.upvoters = upvoters;
    }
}
