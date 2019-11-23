package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum InputParameterType implements EnumClass<String> {

    STRING("STRING"),
    INTEGER("INTEGER"),
    BOOLEAN("BOOLEAN"),
    DATE("DATE"),
    DATETIME("DATETIME"),
    LOCAL_DATE("LOCAL_DATE"),
    LOCAL_DATE_TIME("LOCAL_DATE_TIME"),
    LOCAL_TIME("LOCAL_TIME"),
    ENUMERATION("ENUMERATION"),
    DOUBLE("DOUBLE"),
    BIG_DECIMAL("BIG_DECIMAL"),
    ENTITY("ENTITY");

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