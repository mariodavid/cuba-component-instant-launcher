package de.diedavids.cuba.instantlauncher.web.launcher.menu;

import com.haulmont.cuba.gui.components.mainwindow.SideMenu;

public class SideMenuItemAdapter implements MenuItemAdapter<SideMenu.MenuItem> {

    private final SideMenu menu;
    private final SideMenu.MenuItem menuItem;

    private SideMenuItemAdapter(SideMenu menu, SideMenu.MenuItem menuItem) {
        this.menu = menu;
        this.menuItem = menuItem;
    }

    public static SideMenuItemAdapter of(SideMenu menu, SideMenu.MenuItem menuItem) {
        return new SideMenuItemAdapter(menu, menuItem);
    }

    @Override
    public void addChildItem(MenuItemAdapter<SideMenu.MenuItem> subItem) {
        menuItem.addChildItem(subItem.getMenuItem());
    }

    @Override
    public SideMenu.MenuItem getMenuItem() {
        return menuItem;
    }
}
