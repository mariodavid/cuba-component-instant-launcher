package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.gui.WindowManager.OpenType
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import org.springframework.stereotype.Component

@Component('ddcil_GeneralScreenLauncherCommandExecutor')
class GeneralScreenLauncherCommandExecutor extends AbstractScreenLauncherCommandExecutor {

  @Override
  void execute(ScreenLauncherCommand launcherCommand) {
    Map<String, Object> params = getScreenParameters(launcherCommand)
    OpenType openType = toOpenType(launcherCommand.openType)

    frame.openWindow(launcherCommand.screenId, openType, params)
  }
}
