package com.logdb.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Session {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private String id;

    @Column(name = "IP",  nullable = false)
    private String ip;

    @Column(name = "BROWSER",  nullable = false)
    private String browser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

}
