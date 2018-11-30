package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.core.global.Scripting
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor
import spock.lang.Specification

class ScriptLauncherCommandExecutorSpec extends Specification {

  LauncherCommandExecutor sut

  Scripting scripting
  def setup() {
    scripting = Mock(Scripting)

    sut = new ScriptLauncherCommandExecutor(
        scripting: scripting
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
}
