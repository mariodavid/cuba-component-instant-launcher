package de.diedavids.cuba.instantlauncher.web.launcher.executor


import com.haulmont.cuba.gui.WindowManager.OpenType
import com.haulmont.cuba.gui.components.Frame
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommandType
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherOpenType
import spock.lang.Specification

class GeneralScreenLauncherCommandExecutorSpec extends Specification {

    GeneralScreenLauncherCommandExecutor sut
    ScreenParameterScriptEvaluation screenParameterScriptEvaluation = Mock(ScreenParameterScriptEvaluation)
    Frame frame = Mock(Frame)

    def setup() {
        sut = new MockableGeneralScreenLauncherCommandExecutor(
                screenParameterScriptEvaluation: screenParameterScriptEvaluation,
                frame: frame
        )
    }

    def "execute fetches the parameter from the params script"() {
        given:
        def launcherCommand = new ScreenLauncherCommand(
                screenParametersScript: "return [foo:'bar']",
                openType: ScreenLauncherOpenType.DIALOG
        )

        when:
        sut.execute(launcherCommand)
        then:
        1 * screenParameterScriptEvaluation.evaluateScreenParameters(launcherCommand, _)
    }

    def "execute passes the result of the params script to the screen"() {
        given:
        def launcherCommand = new ScreenLauncherCommand(
                screenParametersScript: "return [foo:'bar']",
                openType: ScreenLauncherOpenType.DIALOG,
                screenId: 'my$screen',
                screenLauncherCommandType: ScreenLauncherCommandType.GENERAL
        )
        and:
        screenParameterScriptEvaluation.evaluateScreenParameters(launcherCommand, _) >> [foo: 'bar']

        when:
        sut.execute(launcherCommand)
        then:
        1 * frame.openWindow('my$screen', OpenType.DIALOG, [foo: 'bar'])
    }

    def "execute converts the OpenType of the launcher command to a real OpenType"() {
        given:
        def launcherCommand = new ScreenLauncherCommand(
                openType: ScreenLauncherOpenType.DIALOG,
                screenLauncherCommandType: ScreenLauncherCommandType.GENERAL
        )
        when:
        sut.execute(launcherCommand)
        then:
        1 * frame.openWindow(_, OpenType.DIALOG, _)
    }

    def "execute uses NEW_TAB as default openType if nothing is specified"() {
        given:
        def launcherCommand = new ScreenLauncherCommand(
                openType: null
        )
        when:
        sut.execute(launcherCommand)
        then:
        1 * frame.openWindow(_, OpenType.NEW_TAB, _)
    }

    def "execute uses the screen ID to open the correct screen"() {
        given:
        def launcherCommand = new ScreenLauncherCommand(
                screenId: 'my$screen'
        )

        when:
        sut.execute(launcherCommand)
        then:
        1 * frame.openWindow('my$screen', _, _)
    }
}

class MockableGeneralScreenLauncherCommandExecutor extends GeneralScreenLauncherCommandExecutor {

    Frame frame
}