package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.core.global.Scripting
import com.haulmont.cuba.gui.components.Frame
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor
import spock.lang.Specification

class ScriptLauncherCommandExecutorSpec extends Specification {

  LauncherCommandExecutor sut

  Scripting scripting
  private Frame       frame       = Mock(Frame)
  private DataManager dataManager = Mock(DataManager)
  private Messages messages = Mock(Messages)

  def setup() {
    scripting = Mock(Scripting)

    sut = new MockableScriptLauncherCommandExecutor(
        scripting: scripting,
        dataManager: dataManager,
        messages: messages,
        frame: frame
    )
  }

  def "executes triggers the scripting API"() {
    given:
    def launcherCommand = new ScriptLauncherCommand(
        launchScript: "4+5"
    )

    when:
    sut.execute(launcherCommand)
    then:
    1 * scripting.evaluateGroovy("4+5", _)
  }

  def "executes passes the frame as a binding"() {
    when:
    sut.execute(new ScriptLauncherCommand())
    then:
    1 * scripting.evaluateGroovy(_, { it.variables.get('frame') == frame })
  }

  def "executes passes dataManager as a binding"() {
    when:
    sut.execute(new ScriptLauncherCommand())
    then:
    1 * scripting.evaluateGroovy(_, { it.variables.get('dataManager') == dataManager })
  }
  def "executes passes messages as a binding"() {
    when:
    sut.execute(new ScriptLauncherCommand())
    then:
    1 * scripting.evaluateGroovy(_, { it.variables.get('messages') == messages })
  }
}

class MockableScriptLauncherCommandExecutor extends ScriptLauncherCommandExecutor {

  Frame frame
}