package com.ticketsystem.model.enumeration;

public enum Role {
    ADMIN("ADMIN"),
    SUPPORTER("SUPPORTER"),
    CUSTOMER("CUSTOMER");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
