package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.cuba.core.global.BeanLocator
import de.diedavids.cuba.instantlauncher.entity.*
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutorFactory
import de.diedavids.cuba.instantlauncher.web.launcher.executor.EditorScreenLauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.executor.GeneralScreenLauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.executor.ScriptLauncherCommandExecutor
import spock.lang.Specification

class LauncherCommandExecutorFactorySpec extends Specification {

  LauncherCommandExecutorFactory sut
  GeneralScreenLauncherCommandExecutor generalScreenLauncherCommandExecutor = Mock(GeneralScreenLauncherCommandExecutor)
  EditorScreenLauncherCommandExecutor editorScreenLauncherCommandExecutor = Mock(EditorScreenLauncherCommandExecutor)
  ScriptLauncherCommandExecutor scriptLauncherCommandExecutor = Mock(ScriptLauncherCommandExecutor)
  BeanLocator beanLocator = Mock(BeanLocator)

  def setup() {
    sut = new LauncherCommandExecutorFactory(
        generalScreenLauncherCommandExecutor: generalScreenLauncherCommandExecutor,
        editorScreenLauncherCommandExecutor: editorScreenLauncherCommandExecutor,
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

  def "create gives the screen executor back in case of a ScreenLaunchCommand with type GENERAL"() {
    given:
    def launcherCommand = new ScreenLauncherCommand(type: LauncherCommandType.SCREEN_LAUNCHER)

    when:
    def result = sut.create(launcherCommand)
    then:
    result == generalScreenLauncherCommandExecutor
  }

  def "create gives the screen executor back in case of a ScreenLaunchCommand with type EDITOR"() {
    given:
    def launcherCommand = new ScreenLauncherCommand(
        type: LauncherCommandType.SCREEN_LAUNCHER,
        screenLauncherCommandType: ScreenLauncherCommandType.EDITOR
    )

    when:
    def result = sut.create(launcherCommand)
    then:
    result == editorScreenLauncherCommandExecutor
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

