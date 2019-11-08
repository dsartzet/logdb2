package com.logdb.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Access
{

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SESSION_ID", unique = true, nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "REQUEST_ID", nullable = false)
    private Request request;

    @ManyToOne
    @JoinColumn(name = "RESPONSE_ID", nullable = false)
    private Response response;

    @Column(name = "ACCESS_TS", nullable = false)
    private Timestamp timestamp;

    @Column(name = "REFERER",  nullable = false)
    private String referer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }
}
