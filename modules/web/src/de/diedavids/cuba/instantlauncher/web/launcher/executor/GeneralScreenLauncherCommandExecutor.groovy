package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.core.global.Scripting
import com.haulmont.cuba.gui.WindowManager.OpenType
import com.haulmont.cuba.gui.components.Frame
import com.haulmont.cuba.web.AppUI
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherOpenType
import org.springframework.stereotype.Component

import javax.inject.Inject

@Component("ddcil_GeneralScreenLauncherCommandExecutor")
class GeneralScreenLauncherCommandExecutor extends AbstractScreenLauncherCommandExecutor {

  @Override
  void execute(ScreenLauncherCommand launcherCommand) {
    Map<String, Object> params = getScreenParameters(launcherCommand)
    OpenType openType = toOpenType(launcherCommand.getOpenType())

    frame.openWindow(launcherCommand.getScreenId(), openType, params)
  }
}
