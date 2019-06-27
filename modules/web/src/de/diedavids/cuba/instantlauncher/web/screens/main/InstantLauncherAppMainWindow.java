package de.diedavids.cuba.instantlauncher.web.screens.main;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.KeyCombination;
import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;
import com.haulmont.cuba.web.gui.components.util.ShortcutListenerDelegate;
import com.haulmont.cuba.web.widgets.CubaOrderedActionsLayout;
import com.vaadin.event.ShortcutListener;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutorAPI;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandShortcutInitializer;
import de.diedavids.cuba.instantlauncher.web.launcher.repository.LauncherCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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