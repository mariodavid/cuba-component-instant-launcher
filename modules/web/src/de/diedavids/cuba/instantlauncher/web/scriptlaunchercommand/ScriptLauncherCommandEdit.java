package de.diedavids.cuba.instantlauncher.web.scriptlaunchercommand;

import com.haulmont.cuba.gui.components.AbstractEditor;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandType;
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand;

public class ScriptLauncherCommandEdit extends AbstractEditor<ScriptLauncherCommand> {


  @Override
  protected void initNewItem(ScriptLauncherCommand item) {
    super.initNewItem(item);
    item.setType(LauncherCommandType.SCRIPT_LAUNCHER);
  }
}