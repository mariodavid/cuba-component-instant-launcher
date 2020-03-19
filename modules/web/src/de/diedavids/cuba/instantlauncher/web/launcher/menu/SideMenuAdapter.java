package de.diedavids.cuba.instantlauncher.web.launcher.menu;

import com.haulmont.cuba.gui.components.mainwindow.SideMenu;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SideMenuAdapter implements MenuAdapter<SideMenu.MenuItem> {

    private final SideMenu menu;

    private SideMenuAdapter(SideMenu menu) {
        this.menu = menu;
    }

    public static SideMenuAdapter of(SideMenu appMenu) {
        return new SideMenuAdapter(appMenu);
    }

    @Override
    public MenuItemAdapter createMenuItem(String id, String caption) {
        SideMenu.MenuItem menuItem = menu.createMenuItem(id, caption);
        return SideMenuItemAdapter.of(menu, menuItem);
    }

    @Override
    public MenuItemAdapter createMenuItem(
            String id,
            String caption,
            @Nullable String icon,
            @Nullable Consumer<SideMenu.MenuItem> command
    ) {
        SideMenu.MenuItem menuItem = menu.createMenuItem(id, caption, icon, command);
        return SideMenuItemAdapter.of(menu, menuItem);
    }

    @Override
    public void addMenuItem(SideMenu.MenuItem menuItem) {
        menu.addMenuItem(menuItem);
    }

    @Override
    public void addMenuItem(SideMenu.MenuItem menuItem, int index) {
        menu.addMenuItem(menuItem, index);
    }
}
