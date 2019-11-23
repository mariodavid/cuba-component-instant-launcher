package de.diedavids.cuba.instantlauncher.web.screens.main;

import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandShortcutInitializer;

import javax.inject.Inject;

public class InstantLauncherAppMainWindow extends AppMainWindow {

    @Inject
    protected LauncherCommandShortcutInitializer launcherCommandShortcutInitializer;


    @Override
    public void ready() {
        super.ready();

        launcherCommandShortcutInitializer.initInstantLauncherShortcuts(
                (RootWindow) getFrameOwner().getWindow()
        );
    }
}