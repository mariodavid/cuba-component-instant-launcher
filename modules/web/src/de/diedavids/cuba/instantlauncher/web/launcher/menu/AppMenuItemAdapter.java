package de.diedavids.cuba.instantlauncher.web.launcher.menu;

import com.haulmont.cuba.gui.components.mainwindow.AppMenu;

public class AppMenuItemAdapter implements MenuItemAdapter<AppMenu.MenuItem> {

    private final AppMenu appMenu;
    private final AppMenu.MenuItem menuItem;

    private AppMenuItemAdapter(AppMenu appMenu, AppMenu.MenuItem menuItem) {
        this.appMenu = appMenu;
        this.menuItem = menuItem;
    }

    public static AppMenuItemAdapter of(AppMenu appMenu, AppMenu.MenuItem menuItem) {
        return new AppMenuItemAdapter(appMenu, menuItem);
    }

    @Override
    public void addChildItem(MenuItemAdapter<AppMenu.MenuItem> subItem) {
        menuItem.addChildItem(subItem.getMenuItem());
    }

    @Override
    public AppMenu.MenuItem getMenuItem() {
        return menuItem;
    }
}
