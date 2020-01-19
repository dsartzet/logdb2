package com.logdb2.document;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

public class Upvote {
    @Indexed
    private ObjectId log;
    @Indexed
    private String sourceIp;

    public Upvote(ObjectId log, String sourceIp) {
        this.log = log;
        this.sourceIp = sourceIp;
    }

    public ObjectId getLog() {
        return log;
    }

    public void setLog(ObjectId log) {
        this.log = log;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }
}
