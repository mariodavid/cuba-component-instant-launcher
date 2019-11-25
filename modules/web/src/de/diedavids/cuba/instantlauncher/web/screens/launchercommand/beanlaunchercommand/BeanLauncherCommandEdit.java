package de.diedavids.cuba.instantlauncher.web.screens.launchercommand.beanlaunchercommand;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.BeanLauncherCommand;

@UiController("ddcil$BeanLauncherCommand.edit")
@UiDescriptor("bean-launcher-command-edit.xml")
@EditedEntityContainer("beanLauncherCommandDc")
@LoadDataBeforeShow
public class BeanLauncherCommandEdit extends StandardEditor<BeanLauncherCommand> {
}