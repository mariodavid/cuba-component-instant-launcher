package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.gui.WindowManager.OpenType
import com.haulmont.cuba.gui.components.Frame
import com.haulmont.cuba.web.AppUI
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherOpenType
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor
import org.springframework.stereotype.Component

import javax.inject.Inject

@Component('ddcil_GeneralScreenLauncherCommandExecutor')
class GeneralScreenLauncherCommandExecutor implements LauncherCommandExecutor<ScreenLauncherCommand> {

  @Inject
  ScreenParameterScriptEvaluation screenParameterScriptEvaluation

  @Override
  void execute(ScreenLauncherCommand launcherCommand, Map<String, Object> inputParams) {
    Map<String, Object> params = screenParameterScriptEvaluation.evaluateScreenParameters(launcherCommand, inputParams)
    OpenType openType = toOpenType(launcherCommand.openType)

    frame.openWindow(launcherCommand.screenId, openType, params)
  }

  @Override
  void execute(ScreenLauncherCommand launcherCommand) {
    execute(launcherCommand, [:])
  }

  protected OpenType toOpenType(ScreenLauncherOpenType screenLauncherOpenType) {
    screenLauncherOpenType ? OpenType.valueOf(screenLauncherOpenType.toString()) : OpenType.NEW_TAB
  }

  protected Frame getFrame() {
    AppUI.current.topLevelWindow
  }
}
