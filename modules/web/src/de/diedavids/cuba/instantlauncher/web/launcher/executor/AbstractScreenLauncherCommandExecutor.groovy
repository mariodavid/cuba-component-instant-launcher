package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.core.global.Scripting
import com.haulmont.cuba.gui.WindowManager.OpenType
import com.haulmont.cuba.gui.components.Frame
import com.haulmont.cuba.web.AppUI
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherOpenType
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor
import groovy.util.logging.Slf4j

import javax.inject.Inject

@Slf4j
abstract class AbstractScreenLauncherCommandExecutor implements LauncherCommandExecutor<ScreenLauncherCommand> {

  @Inject
  Scripting scripting


  protected Map<String, Object> getScreenParameters(ScreenLauncherCommand screenLauncherCommand) {

    try {
      def screenParametersScript = scripting.evaluateGroovy(screenLauncherCommand.getScreenParametersScript(), new Binding())

      if (screenParametersScript != null && screenParametersScript instanceof Map) {
        return screenParametersScript
      } else {
        return [:]
      }
    }
    catch (Exception e) {
      log.error("Error while executing screen parameters script", e)
      return [:]
    }
  }

  protected OpenType toOpenType(ScreenLauncherOpenType screenLauncherOpenType) {
    screenLauncherOpenType ? OpenType.valueOf(screenLauncherOpenType.toString()) : OpenType.NEW_TAB
  }

  protected Frame getFrame() {
    AppUI.getCurrent()
         .getTopLevelWindow()
  }
}
