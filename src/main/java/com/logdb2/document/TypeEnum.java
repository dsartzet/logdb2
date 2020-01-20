package com.logdb2.document;

public enum TypeEnum {
    ACCESS(0),
    SERVED(1),
    RECEIVING(2),
    RECEIVED(3),
    REPLICATE(4),
    DELETE(5);

    private final int value;

    TypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
