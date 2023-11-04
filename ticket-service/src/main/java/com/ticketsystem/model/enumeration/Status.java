package com.ticketsystem.model.enumeration;

public enum Status {
    OPEN("OPEN", true),
    CLOSE("CLOSE", false);

    private String code;
    private boolean value;

    Status(String code, boolean value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public boolean isValue() {
        return value;
    }
}
