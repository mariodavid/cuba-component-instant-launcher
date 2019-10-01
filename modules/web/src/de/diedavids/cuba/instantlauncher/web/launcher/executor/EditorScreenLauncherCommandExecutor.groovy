package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.bali.util.ParamsMap
import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.gui.WindowManager.OpenType
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import org.springframework.stereotype.Component

import javax.inject.Inject

@Component('ddcil_EditorScreenLauncherCommandExecutor')
class EditorScreenLauncherCommandExecutor extends AbstractScreenLauncherCommandExecutor {

  @Inject
  Metadata metadata

  @Override
  void execute(ScreenLauncherCommand launcherCommand, Map<String, Object> inputParams) {
    Entity entity = metadata.create(launcherCommand.screenEntity)
    Map<String, Object> params = getScreenParameters(launcherCommand)
    OpenType openType = toOpenType(launcherCommand.openType)

    frame.openEditor(launcherCommand.screenId, entity, openType, params)
  }

  @Override
  void execute(ScreenLauncherCommand launcherCommand) {
    execute(launcherCommand, ParamsMap.empty())
  }


}
