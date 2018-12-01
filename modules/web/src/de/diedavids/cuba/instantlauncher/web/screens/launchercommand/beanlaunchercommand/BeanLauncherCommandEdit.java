package de.diedavids.cuba.instantlauncher.web.screens.launchercommand.beanlaunchercommand;

import com.haulmont.cuba.gui.components.AbstractEditor;
import de.diedavids.cuba.instantlauncher.entity.BeanLauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandType;

public class BeanLauncherCommandEdit extends AbstractEditor<BeanLauncherCommand> {

  @Override
  protected void initNewItem(BeanLauncherCommand item) {
    super.initNewItem(item);
    item.setType(LauncherCommandType.BEAN_LAUNCHER);
  }
}