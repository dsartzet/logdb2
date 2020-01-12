package com.logdb2.document;

public enum HttpMethodEnum {
    GET(0),
    POST(1),
    PUT(2),
    DELETE(3),
    PROPFIND(4),
    HEAD(5),
    OPTIONS(6);

    private final int value;

    HttpMethodEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
