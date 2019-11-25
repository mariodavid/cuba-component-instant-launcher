package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum LauncherCommandType implements EnumClass<String> {

    SCREEN_LAUNCHER("SCREEN_LAUNCHER"),
    SCRIPT_LAUNCHER("SCRIPT_LAUNCHER"),
    BEAN_LAUNCHER("BEAN_LAUNCHER");

    private String id;

    LauncherCommandType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static LauncherCommandType fromId(String id) {
        for (LauncherCommandType at : LauncherCommandType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}