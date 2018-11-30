package de.diedavids.cuba.instantlauncher.web.components

import com.haulmont.cuba.core.global.BeanLocator
import de.diedavids.cuba.instantlauncher.entity.BeanLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandType
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.ScreenLauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.ScriptLauncherCommandExecutor
import spock.lang.Specification

class LauncherCommandExecutorFactorySpec extends Specification {

  LauncherCommandExecutorFactory sut
  ScreenLauncherCommandExecutor screenLauncherCommandExecutor = Mock(ScreenLauncherCommandExecutor)
  ScriptLauncherCommandExecutor scriptLauncherCommandExecutor = Mock(ScriptLauncherCommandExecutor)
  BeanLocator beanLocator = Mock(BeanLocator)

  def setup() {
    sut = new LauncherCommandExecutorFactory(
        screenLauncherCommandExecutor: screenLauncherCommandExecutor,
        scriptLauncherCommandExecutor: scriptLauncherCommandExecutor,
        beanLocator: beanLocator
    )
  }

  def "create gives the script executor back in case of a ScriptLaunchCommand"() {
    given:
    def launcherCommand = new ScriptLauncherCommand(type: LauncherCommandType.SCRIPT_LAUNCHER)

    when:
    def result = sut.create(launcherCommand)
    then:
    result == scriptLauncherCommandExecutor
  }

  def "create gives the screen executor back in case of a ScreenLaunchCommand"() {
    given:
    def launcherCommand = new ScreenLauncherCommand(type: LauncherCommandType.SCREEN_LAUNCHER)

    when:
    def result = sut.create(launcherCommand)
    then:
    result == screenLauncherCommandExecutor
  }

  def "create searches for a Spring bean of given name when type is BeanLaunchCommand"() {
    given:
    def launcherCommand = new BeanLauncherCommand(
        type: LauncherCommandType.BEAN_LAUNCHER,
        beanName: "myBean"
    )

    def mockedBean = Mock(LauncherCommandExecutor)
    and:
    beanLocator.get("myBean") >> mockedBean
    when:
    def result = sut.create(launcherCommand)
    then:
    result == mockedBean
  }
}

