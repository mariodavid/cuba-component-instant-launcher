package de.diedavids.cuba.instantlauncher.web.screens.launchercommandgroup;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandGroup;

@UiController("ddcil$LauncherCommandGroup.edit")
@UiDescriptor("launcher-command-group-edit.xml")
@EditedEntityContainer("launcherCommandGroupDc")
@LoadDataBeforeShow
public class LauncherCommandGroupEdit extends StandardEditor<LauncherCommandGroup> {
}