package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.core.global.Scripting
import com.haulmont.cuba.gui.WindowManager.OpenType
import com.haulmont.cuba.gui.components.Window.TopLevelWindow
import com.haulmont.cuba.web.AppUI
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommandType
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherOpenType
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import javax.inject.Inject

@Component("ddcil_ScreenLauncherCommandExecutor")
@Slf4j
class ScreenLauncherCommandExecutor implements LauncherCommandExecutor<ScreenLauncherCommand> {

  @Inject
  Scripting scripting

  @Inject
  Metadata metadata

  @Override
  void execute(ScreenLauncherCommand launcherCommand) {
    if (launcherCommand.screenLauncherCommandType == ScreenLauncherCommandType.EDITOR) {
      executeEditScreenLauncherCommand(launcherCommand)
    } else {
      executeGeneralScreenLauncherCommand(launcherCommand)
    }
  }

  void executeGeneralScreenLauncherCommand(ScreenLauncherCommand screenLauncherCommand) {

    Map<String, Object> params = getScreenParameters(screenLauncherCommand)

    topLevelWindow
        .openWindow(screenLauncherCommand.getScreenId(), toOpenType(screenLauncherCommand.getOpenType()), params)
  }

  protected void executeEditScreenLauncherCommand(ScreenLauncherCommand screenLauncherCommand) {
    Entity entity = metadata.create(screenLauncherCommand.getScreenEntity())

    Map<String, Object> params = getScreenParameters(screenLauncherCommand)

    topLevelWindow
        .openEditor(screenLauncherCommand.getScreenId(), entity, toOpenType(screenLauncherCommand.getOpenType()), params)
  }

  protected Map<String, Object> getScreenParameters(ScreenLauncherCommand screenLauncherCommand) {
    try {
      return scripting.evaluateGroovy(screenLauncherCommand.getScreenParametersScript(), new Binding())
    }
    catch (Exception e) {
      log.error("Error while executing screen parameters script", e)
      return [:]
    }
  }


  protected OpenType toOpenType(ScreenLauncherOpenType screenLauncherOpenType) {
    OpenType.valueOf(screenLauncherOpenType.toString())
  }

  protected TopLevelWindow getTopLevelWindow() {
    AppUI.getCurrent()
         .getTopLevelWindow()
  }
}
