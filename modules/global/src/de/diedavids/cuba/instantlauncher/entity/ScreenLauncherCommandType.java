package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ScreenLauncherCommandType implements EnumClass<String> {

    BROWSE("BROWSE"),
    EDITOR("EDITOR"),
    GENERAL("GENERAL");

    private String id;

    ScreenLauncherCommandType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ScreenLauncherCommandType fromId(String id) {
        for (ScreenLauncherCommandType at : ScreenLauncherCommandType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}