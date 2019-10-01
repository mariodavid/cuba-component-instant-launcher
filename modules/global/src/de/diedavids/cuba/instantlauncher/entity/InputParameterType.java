package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum InputParameterType implements EnumClass<String> {

    STRING("STRING"),
    INTEGER("INTEGER"),
    BOOLEAN("BOOLEAN"),
    DATE("DATE"),
    DATETIME("DATETIME");

    private String id;

    InputParameterType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static InputParameterType fromId(String id) {
        for (InputParameterType at : InputParameterType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}