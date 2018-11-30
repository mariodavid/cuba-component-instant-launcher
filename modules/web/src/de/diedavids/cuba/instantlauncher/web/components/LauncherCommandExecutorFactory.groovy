package de.diedavids.cuba.instantlauncher.web.components

import com.haulmont.cuba.core.global.BeanLocator
import com.haulmont.cuba.core.global.DataManager
import de.diedavids.cuba.instantlauncher.entity.BeanLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandType
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.ScreenLauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.ScriptLauncherCommandExecutor
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import javax.inject.Inject

@Component("ddcil_LauncherCommandExecutorFactory")
@Slf4j
class LauncherCommandExecutorFactory {

  @Inject
  BeanLocator beanLocator

  @Inject
  ScriptLauncherCommandExecutor scriptLauncherCommandExecutor

  @Inject
  ScreenLauncherCommandExecutor screenLauncherCommandExecutor


  LauncherCommandExecutor create(LauncherCommand launcherCommand) {

    switch (launcherCommand.type) {
      case LauncherCommandType.BEAN_LAUNCHER: return createBeanLaunchCommandExecutor(launcherCommand as BeanLauncherCommand)
      case LauncherCommandType.SCRIPT_LAUNCHER: return scriptLauncherCommandExecutor
      case LauncherCommandType.SCREEN_LAUNCHER: return screenLauncherCommandExecutor
      default: return null
    }
  }

  LauncherCommandExecutor createBeanLaunchCommandExecutor(BeanLauncherCommand launcherCommand) {
    beanLocator.get(launcherCommand.getBeanName())
  }
}