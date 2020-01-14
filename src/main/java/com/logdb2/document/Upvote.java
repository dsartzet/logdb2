package com.logdb2.document;

import org.bson.types.ObjectId;

public class Upvote {
    private ObjectId log;
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
