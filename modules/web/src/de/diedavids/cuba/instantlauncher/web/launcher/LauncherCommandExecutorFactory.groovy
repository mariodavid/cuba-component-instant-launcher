package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.cuba.core.global.BeanLocator
import de.diedavids.cuba.instantlauncher.entity.BeanLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandType
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommandType
import de.diedavids.cuba.instantlauncher.web.launcher.executor.AbstractScreenLauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.executor.EditorScreenLauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.executor.GeneralScreenLauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.executor.ScriptLauncherCommandExecutor
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
  GeneralScreenLauncherCommandExecutor generalScreenLauncherCommandExecutor

  @Inject
  EditorScreenLauncherCommandExecutor editorScreenLauncherCommandExecutor


  LauncherCommandExecutor create(LauncherCommand launcherCommand) {

    switch (launcherCommand.type) {
      case LauncherCommandType.BEAN_LAUNCHER: return createBeanLaunchCommandExecutor(launcherCommand as BeanLauncherCommand)
      case LauncherCommandType.SCRIPT_LAUNCHER: return scriptLauncherCommandExecutor
      case LauncherCommandType.SCREEN_LAUNCHER: return findScreenLaunchCommandExecutor(launcherCommand as ScreenLauncherCommand)
      default: return null
    }
  }

  LauncherCommandExecutor findScreenLaunchCommandExecutor(ScreenLauncherCommand launcherCommand) {
    launcherCommand.screenLauncherCommandType == ScreenLauncherCommandType.EDITOR ? editorScreenLauncherCommandExecutor : generalScreenLauncherCommandExecutor
  }

  LauncherCommandExecutor createBeanLaunchCommandExecutor(BeanLauncherCommand launcherCommand) {
    beanLocator.get(launcherCommand.getBeanName())
  }
}