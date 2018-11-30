package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ScreenLauncherOpenType implements EnumClass<String> {

    NEW_TAB("NEW_TAB"),
    DIALOG("DIALOG");

    private String id;

    ScreenLauncherOpenType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ScreenLauncherOpenType fromId(String id) {
        for (ScreenLauncherOpenType at : ScreenLauncherOpenType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}