package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.gui.WindowManager.OpenType
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import org.springframework.stereotype.Component

import javax.inject.Inject

@Component("ddcil_EditorScreenLauncherCommandExecutor")
class EditorScreenLauncherCommandExecutor extends AbstractScreenLauncherCommandExecutor {

  @Inject
  Metadata metadata

  @Override
  void execute(ScreenLauncherCommand launcherCommand) {
    Entity entity = metadata.create(launcherCommand.getScreenEntity())
    Map<String, Object> params = getScreenParameters(launcherCommand)
    OpenType openType = toOpenType(launcherCommand.getOpenType())

    frame.openEditor(launcherCommand.getScreenId(), entity, openType, params)
  }


}
