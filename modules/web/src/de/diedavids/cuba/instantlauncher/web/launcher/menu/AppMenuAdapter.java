package de.diedavids.cuba.instantlauncher.web.launcher.menu;

import com.haulmont.cuba.gui.components.mainwindow.AppMenu;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class AppMenuAdapter implements MenuAdapter<AppMenu.MenuItem> {

    private final AppMenu appMenu;

    private AppMenuAdapter(AppMenu appMenu) {
        this.appMenu = appMenu;
    }

    public static AppMenuAdapter of(AppMenu appMenu) {
        return new AppMenuAdapter(appMenu);
    }

    @Override
    public MenuItemAdapter createMenuItem(String id, String caption) {
        AppMenu.MenuItem menuItem = appMenu.createMenuItem(id, caption);
        return AppMenuItemAdapter.of(appMenu, menuItem);
    }

    @Override
    public MenuItemAdapter createMenuItem(
            String id,
            String caption,
            @Nullable String icon,
            @Nullable Consumer<AppMenu.MenuItem> command
    ) {
        AppMenu.MenuItem menuItem = appMenu.createMenuItem(id, caption, icon, command);
        return AppMenuItemAdapter.of(appMenu, menuItem);
    }

    @Override
    public void addMenuItem(AppMenu.MenuItem menuItem) {
        appMenu.addMenuItem(menuItem);
    }

    @Override
    public void addMenuItem(AppMenu.MenuItem menuItem, int index) {
        appMenu.addMenuItem(menuItem, index);
    }
}
