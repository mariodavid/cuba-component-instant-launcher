package de.diedavids.cuba.instantlauncher.web.launcher.menu;

public interface MenuItemAdapter<T> {

    void addChildItem(MenuItemAdapter<T> subItem);

    T getMenuItem();
}
