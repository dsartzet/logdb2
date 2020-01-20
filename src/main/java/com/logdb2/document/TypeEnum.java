package com.logdb2.document;

public enum TypeEnum {
    ACCESS(0),
    SERVED(1),
    RECEIVING(2),
    RECEIVED(3),
    REPLICATE(4),
    DELETE(5);

    private final int value;
    // making sure TypeEnum.values() gets called once.
    private static final TypeEnum[] values = TypeEnum.values();

    TypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TypeEnum[] getValues() {
        return values;
    }
}
