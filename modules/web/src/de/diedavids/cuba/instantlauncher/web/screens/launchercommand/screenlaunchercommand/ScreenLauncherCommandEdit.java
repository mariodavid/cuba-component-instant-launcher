package de.diedavids.cuba.instantlauncher.web.screens.launchercommand.screenlaunchercommand;

import com.haulmont.cuba.gui.components.AbstractEditor;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandType;
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand;

public class ScreenLauncherCommandEdit extends AbstractEditor<ScreenLauncherCommand> {


  @Override
  protected void initNewItem(ScreenLauncherCommand item) {
    super.initNewItem(item);
    item.setType(LauncherCommandType.SCREEN_LAUNCHER);
  }
}