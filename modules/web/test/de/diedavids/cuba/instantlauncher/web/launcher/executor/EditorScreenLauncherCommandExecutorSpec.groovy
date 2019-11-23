package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.gui.WindowManager.OpenType
import com.haulmont.cuba.gui.components.Frame
import com.haulmont.cuba.security.entity.User
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommandType
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherOpenType
import spock.lang.Specification

class EditorScreenLauncherCommandExecutorSpec extends Specification {

  EditorScreenLauncherCommandExecutor sut
  private ScreenParameterScriptEvaluation screenParameterScriptEvaluation = Mock(ScreenParameterScriptEvaluation)
  private Frame    frame    = Mock(Frame)
  private Metadata metadata = Mock(Metadata)

  def setup() {
    sut = new MockableEditorScreenLauncherCommandExecutor(
        frame: frame,
        metadata: metadata,
            screenParameterScriptEvaluation: screenParameterScriptEvaluation
    )
  }

  private void paramsAreEvaluatedWith(Map<String, Object> params) {
    screenParameterScriptEvaluation.evaluateScreenParameters(_, _) >> params
  }

  def "execute passes the result of the params script to the screen"() {
    given:
    def launcherCommand = new ScreenLauncherCommand(
        screenParametersScript: "return [foo:'bar']",
        openType: ScreenLauncherOpenType.DIALOG,
        screenId: 'my$screen',
        screenLauncherCommandType: ScreenLauncherCommandType.EDITOR,
        screenEntity: 'sec$User'
    )
    and:
    paramsAreEvaluatedWith(foo: 'bar')

    and:
    def newUser = new User()
    metadata.create('sec$User') >> newUser

    when:
    sut.execute(launcherCommand)

    then:
    1 * frame.openEditor('my$screen', newUser, OpenType.DIALOG, [foo: 'bar'])
  }


  def "execute creates an instance of the defined screenEntity"() {
    given:
    def launcherCommand = new ScreenLauncherCommand(
        screenId: 'my$screen',
        screenEntity: 'sec$User',
        screenLauncherCommandType: ScreenLauncherCommandType.EDITOR
    )

    and:
    paramsAreEvaluatedWith([:])

    when:
    sut.execute(launcherCommand)

    then:
    1 * metadata.create('sec$User')
  }


  def "execute uses the screen ID to open the correct screen"() {
    given:
    def launcherCommand = new ScreenLauncherCommand(
        screenId: 'my$screen',
        screenEntity: 'sec$User',
        openType: ScreenLauncherOpenType.DIALOG,
        screenLauncherCommandType: ScreenLauncherCommandType.EDITOR
    )

    and:
    paramsAreEvaluatedWith([:])
    and:
    def newUser = new User()
    metadata.create('sec$User') >> newUser

    when:
    sut.execute(launcherCommand)

    then:
    1 * frame.openEditor('my$screen',newUser,OpenType.DIALOG,[:])
  }

}

class MockableEditorScreenLauncherCommandExecutor extends EditorScreenLauncherCommandExecutor {
  Frame frame
}