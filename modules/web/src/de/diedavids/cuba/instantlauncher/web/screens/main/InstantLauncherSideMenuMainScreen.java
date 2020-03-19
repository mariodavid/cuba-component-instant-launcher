package de.diedavids.cuba.instantlauncher.web.screens.main;

import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.gui.components.mainwindow.AppMenu;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.app.main.MainScreen;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandsInitializer;
import de.diedavids.cuba.instantlauncher.web.launcher.menu.SideMenuAdapter;

import javax.inject.Inject;


@UiController("instantLauncherSideMenuMainScreen")
@UiDescriptor("instant-launcher-side-menu-main-screen.xml")
public class InstantLauncherSideMenuMainScreen extends MainScreen {

    @Inject
    protected LauncherCommandsInitializer launcherCommandShortcutInitializer;

    @Inject
    protected SideMenu sideMenu;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {

        launcherCommandShortcutInitializer.initKeyboardShortcuts(
                (RootWindow) this.getWindow()
        );

        launcherCommandShortcutInitializer.initMenuLauncherCommands(
                SideMenuAdapter.of(sideMenu)
        );

    }

}