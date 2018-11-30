package de.diedavids.cuba.instantlauncher.web.beanlaunchercommand;

import com.haulmont.cuba.gui.components.AbstractEditor;
import de.diedavids.cuba.instantlauncher.entity.BeanLauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandType;
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand;

public class BeanLauncherCommandEdit extends AbstractEditor<BeanLauncherCommand> {

  @Override
  protected void initNewItem(BeanLauncherCommand item) {
    super.initNewItem(item);
    item.setType(LauncherCommandType.BEAN_LAUNCHER);
  }
}