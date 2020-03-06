package de.diedavids.cuba.instantlauncher.web.launcher.menu;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public interface MenuAdapter<T> {

    MenuItemAdapter createMenuItem(String id, String caption);
    MenuItemAdapter createMenuItem(String id, String caption, @Nullable String icon, @Nullable Consumer<T> command);

    void addMenuItem(T menuItem);

    void addMenuItem(T menuItem, int index);
}
