package com.kaweel.jpa.specification.enums;

import lombok.Getter;

@Getter
public enum AddressType {
    OFFICE("OFFICE"),
    HOME("HOME");

    private String value;

    AddressType(String value) {
        this.value = value;
    }

    public boolean isEqual(String name) {
        return value.equalsIgnoreCase(name);
    }
}
