package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.core.global.Scripting
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherOpenType
import spock.lang.Specification

class ScreenParameterScriptEvaluationSpec extends Specification {

    Scripting scripting
    ScreenParameterScriptEvaluation sut

    def setup() {
        scripting = Mock(Scripting)
        sut = new ScreenParameterScriptEvaluation(
                scripting: scripting
        )
    }

    def "evaluateScreenParameters fetches the parameter from the params script"() {
        given:
        def launcherCommand = commandWithScript("return [foo:'bar']")

        when:
        sut.evaluateScreenParameters(launcherCommand, [:])
        then:
        1 * scripting.evaluateGroovy("return [foo:'bar']",_)
    }

    def "evaluateScreenParameters passes the input parameters to the screen parameters script"() {
        given:
        def launcherCommand = commandWithScript("return [foo:'bar']")

        when:
        sut.evaluateScreenParameters(launcherCommand, [foo: "blub"])
        then:
        1 * scripting.evaluateGroovy("return [foo:'bar']",[foo: "blub"])
    }

    def "evaluateScreenParameters merges the result of the script with the input parameters"() {
        given:
        def launcherCommand = commandWithScript("return [foo:'bar']")

        and:
        scripting.evaluateGroovy("return [foo:'bar']", _) >> [foo: "bar"]

        when:
        def result = sut.evaluateScreenParameters(launcherCommand, [bar: "blub"])
        then:
        result == [foo: "bar", bar: "blub"]
    }

    def "evaluateScreenParameters overrides the values from the input parameters if the same key is returned in the screen params script"() {
        given:
        def launcherCommand = commandWithScript("return [foo:'bar']")

        and:
        scripting.evaluateGroovy("return [foo:'bar']", _) >> [foo: "bar"]

        when:
        def result = sut.evaluateScreenParameters(launcherCommand, [foo: "myUserInput"])
        then:
        result == [foo: "bar"]
    }

    private ScreenLauncherCommand commandWithScript(String script) {
        new ScreenLauncherCommand(
                screenParametersScript: script,
                openType: ScreenLauncherOpenType.DIALOG
        )
    }

    def "evaluateScreenParameters executes no groovy evaluation if the screen params script is empty"() {
        given:
        def launcherCommand = new ScreenLauncherCommand(
                screenParametersScript: null,
                openType: ScreenLauncherOpenType.DIALOG
        )

        when:
        sut.evaluateScreenParameters(launcherCommand, [:])
        then:
        0 * scripting.evaluateGroovy(_,_)
    }
}
