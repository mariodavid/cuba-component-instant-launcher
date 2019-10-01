package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.cuba.core.global.DataManager
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutorAPI
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutorAPIBean
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutorFactory
import spock.lang.Specification

class LauncherCommandExecutorAPIBeanSpec extends Specification {

  LauncherCommandExecutorAPI sut
  private LauncherCommandExecutorFactory launcherCommandExecutorFactory = Mock(LauncherCommandExecutorFactory)
  private DataManager                    dataManager = Mock(DataManager)

  def setup() {
    sut = new LauncherCommandExecutorAPIBean(
        dataManager: dataManager,
        launcherCommandExecutorFactory: launcherCommandExecutorFactory
    )
  }

  def "launchCommand reloads the launchCommand and fetches the executor from the factory"() {
    given:
    def launcherCommand = new ScreenLauncherCommand()

    and:
    dataManager.reload(launcherCommand, 'launcherCommand-with-details') >> launcherCommand
    when:
    sut.launchCommand(launcherCommand)
    then:
    1 * launcherCommandExecutorFactory.create(launcherCommand) >> Mock(LauncherCommandExecutor)
  }

  def "launchCommands executes the launch command executor"() {
    given:
    def launcherCommand = new ScreenLauncherCommand()

    and:
    dataManager.reload(launcherCommand, 'launcherCommand-with-details') >> launcherCommand
    and:
    LauncherCommandExecutor executor = Mock(LauncherCommandExecutor)
    launcherCommandExecutorFactory.create(launcherCommand) >> executor
    when:
    sut.launchCommand(launcherCommand)
    then:
    1 * executor.execute(launcherCommand)
  }

}
