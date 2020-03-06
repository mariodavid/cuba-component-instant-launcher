package de.diedavids.cuba.instantlauncher.web.screens.main;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.gui.components.mainwindow.AppMenu;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandGroup;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutorAPI;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandsInitializer;
import de.diedavids.cuba.instantlauncher.web.launcher.menu.AppMenuAdapter;
import de.diedavids.cuba.instantlauncher.web.launcher.menu.AppMenuItemAdapter;
import de.diedavids.cuba.instantlauncher.web.launcher.repository.LauncherCommandRepository;

import javax.inject.Inject;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;

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