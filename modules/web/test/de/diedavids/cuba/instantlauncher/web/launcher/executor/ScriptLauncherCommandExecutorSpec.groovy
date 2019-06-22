package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.core.global.Scripting
import com.haulmont.cuba.gui.Dialogs
import com.haulmont.cuba.gui.Notifications
import com.haulmont.cuba.gui.components.Frame
import com.haulmont.cuba.gui.screen.Screen
import com.haulmont.cuba.gui.screen.ScreenContext
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor
import spock.lang.Specification

class ScriptLauncherCommandExecutorSpec extends Specification {

  LauncherCommandExecutor sut

  Scripting scripting
  private Frame       frame       = Mock(Frame)
  private DataManager dataManager = Mock(DataManager)
  private Messages messages = Mock(Messages)
  private ScreenContext screenContext
  private Notifications notifications
  private Dialogs dialogs

  def setup() {
    scripting = Mock(Scripting)

    screenContext = Mock(ScreenContext)

    notifications = Mock(Notifications)
    screenContext.getNotifications() >> notifications
    dialogs = Mock(Dialogs)
    screenContext.getDialogs() >> dialogs
    sut = new MockableScriptLauncherCommandExecutor(
        scripting: scripting,
        dataManager: dataManager,
        messages: messages,
        frame: frame,
        screenContext: screenContext
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

  def "executes passes attributes from the screen context as a binding"() {
    when:
    sut.execute(new ScriptLauncherCommand())
    then:
    1 * scripting.evaluateGroovy(_, {
      it.variables.get('notifications') == notifications &&
              it.variables.get('dialogs') == dialogs
    })
  }

  def "executes passes the screen as a binding"() {
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
  ScreenContext screenContext
}