package de.diedavids.cuba.instantlauncher.web.screens.main;

import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.app.main.MainScreen;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandShortcutInitializer;

import javax.inject.Inject;


@UiController("instantLauncherSideMenuMainScreen")
@UiDescriptor("instant-launcher-side-menu-main-screen.xml")
public class InstantLauncherSideMenuMainScreen extends MainScreen {

    @Inject
    protected LauncherCommandShortcutInitializer launcherCommandShortcutInitializer;


    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {

        launcherCommandShortcutInitializer.initInstantLauncherShortcuts(
                (RootWindow) this.getWindow()
        );
    }

}