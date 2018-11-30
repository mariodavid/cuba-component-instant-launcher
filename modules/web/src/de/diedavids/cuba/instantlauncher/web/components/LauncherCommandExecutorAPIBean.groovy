package de.diedavids.cuba.instantlauncher.web.components

import com.haulmont.cuba.core.global.DataManager
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import javax.inject.Inject

@Component(LauncherCommandExecutorAPI.NAME)
@Slf4j
class LauncherCommandExecutorAPIBean implements LauncherCommandExecutorAPI {

  @Inject
  DataManager dataManager

  @Inject
  LauncherCommandExecutorFactory launcherCommandExecutorFactory


  void launchCommand(LauncherCommand launcherCommand) {

    LauncherCommand reloadedLauncherCommand = dataManager.reload(launcherCommand, "launcherCommand-with-translations")

    def executor = launcherCommandExecutorFactory.create(launcherCommand)
    executor.execute(reloadedLauncherCommand)
  }
}
