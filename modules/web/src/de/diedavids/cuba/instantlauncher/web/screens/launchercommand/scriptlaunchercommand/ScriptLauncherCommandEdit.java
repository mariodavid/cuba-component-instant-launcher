package de.diedavids.cuba.instantlauncher.web.screens.launchercommand.scriptlaunchercommand;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand;

@UiController("ddcil$ScriptLauncherCommand.edit")
@UiDescriptor("script-launcher-command-edit.xml")
@EditedEntityContainer("scriptLauncherCommandDc")
@LoadDataBeforeShow
public class ScriptLauncherCommandEdit extends StandardEditor<ScriptLauncherCommand> {
}