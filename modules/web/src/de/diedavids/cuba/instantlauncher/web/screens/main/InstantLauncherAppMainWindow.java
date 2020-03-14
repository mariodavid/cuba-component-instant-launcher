package de.diedavids.cuba.instantlauncher.web.screens.main;

import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.gui.components.mainwindow.AppMenu;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandsInitializer;
import de.diedavids.cuba.instantlauncher.web.launcher.menu.AppMenuAdapter;

import javax.inject.Inject;

public class InstantLauncherAppMainWindow extends AppMainWindow {

    @Inject
    protected LauncherCommandsInitializer launcherCommandShortcutInitializer;

    @Inject
    private AppMenu mainMenu;

    @Override
    public void ready() {
        super.ready();

        launcherCommandShortcutInitializer.initKeyboardShortcuts(
                (RootWindow) getFrameOwner().getWindow()
        );

        launcherCommandShortcutInitializer.initMenuLauncherCommands(
                AppMenuAdapter.of(mainMenu)
        );
    }
}