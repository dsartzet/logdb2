package com.logdb.document;

public enum HttpMethodEnum {
    GET(1),
    POST(2),
    PUT(3),
    DELETE(4),
    PROPFIND(5),
    HEAD(6),
    OPTIONS(7);

    private final int value;

    HttpMethodEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
