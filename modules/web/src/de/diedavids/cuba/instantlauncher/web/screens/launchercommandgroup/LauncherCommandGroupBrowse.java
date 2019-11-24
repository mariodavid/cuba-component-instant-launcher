package de.diedavids.cuba.instantlauncher.web.screens.launchercommandgroup;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandGroup;

@UiController("ddcil$LauncherCommandGroup.browse")
@UiDescriptor("launcher-command-group-browse.xml")
@LookupComponent("launcherCommandGroupsTable")
@LoadDataBeforeShow
public class LauncherCommandGroupBrowse extends StandardLookup<LauncherCommandGroup> {
}