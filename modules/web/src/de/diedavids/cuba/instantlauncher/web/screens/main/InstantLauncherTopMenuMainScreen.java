package de.diedavids.cuba.instantlauncher.web.screens.main;

import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.gui.components.SizeUnit;
import com.haulmont.cuba.gui.components.SplitPanel;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.components.mainwindow.AppMenu;
import com.haulmont.cuba.gui.components.mainwindow.AppWorkArea;
import com.haulmont.cuba.gui.components.mainwindow.FoldersPane;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.WebConfig;
import com.haulmont.cuba.web.app.main.MainScreen;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.haulmont.cuba.web.widgets.CubaHorizontalSplitPanel;
import com.vaadin.server.Sizeable;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandsInitializer;
import de.diedavids.cuba.instantlauncher.web.launcher.menu.AppMenuAdapter;

import javax.annotation.Nullable;
import javax.inject.Inject;


@UiController("instantLauncherTopMenuMainScreen")
@UiDescriptor("instant-launcher-top-menu-main-screen.xml")
public class InstantLauncherTopMenuMainScreen extends MainScreen implements Window.HasFoldersPane {

    @Inject
    private SplitPanel foldersSplit;
    @Inject
    private FoldersPane foldersPane;
    @Inject
    private AppWorkArea workArea;
    @Inject
    private WebConfig webConfig;

    @Inject
    protected LauncherCommandsInitializer launcherCommandShortcutInitializer;
    @Inject
    protected AppMenu mainMenu;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {

        launcherCommandShortcutInitializer.initKeyboardShortcuts(
                (RootWindow) this.getWindow()
        );

        launcherCommandShortcutInitializer.initMenuLauncherCommands(
                AppMenuAdapter.of(mainMenu)
        );
    }

    public InstantLauncherTopMenuMainScreen() {
        addInitListener(this::initLayout);
    }

    protected void initLayout(@SuppressWarnings("unused") InitEvent event) {
        if (webConfig.getFoldersPaneEnabled()) {
            if (webConfig.getFoldersPaneVisibleByDefault()) {
                foldersSplit.setSplitPosition(webConfig.getFoldersPaneDefaultWidth(), SizeUnit.PIXELS);
            } else {
                foldersSplit.setSplitPosition(0);
            }
            CubaHorizontalSplitPanel vSplitPanel = (CubaHorizontalSplitPanel) WebComponentsHelper.unwrap(foldersSplit);
            vSplitPanel.setDefaultPosition(webConfig.getFoldersPaneDefaultWidth() + "px");
            vSplitPanel.setMaxSplitPosition(50, Sizeable.Unit.PERCENTAGE);
            vSplitPanel.setDockable(true);
        } else {
            foldersPane.setEnabled(false);
            foldersPane.setVisible(false);
            foldersSplit.remove(workArea);
            int foldersSplitIndex = getWindow().indexOf(foldersSplit);
            getWindow().remove(foldersSplit);
            getWindow().add(workArea, foldersSplitIndex);
            getWindow().expand(workArea);
        }
    }

    @Nullable
    @Override
    public FoldersPane getFoldersPane() {
        return foldersPane;
    }

    
    
}