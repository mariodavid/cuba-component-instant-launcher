package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.gui.Dialogs
import de.diedavids.cuba.instantlauncher.entity.InputParameter
import de.diedavids.cuba.instantlauncher.entity.InputParameterTranslation
import de.diedavids.cuba.instantlauncher.entity.InputParameterType
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import spock.lang.Specification

class LauncherCommandExecutorAPIBeanSpec extends Specification {

    LauncherCommandExecutorAPI sut
    LauncherCommandExecutorFactory executorFactory = Mock(LauncherCommandExecutorFactory)
    DataManager dataManager = Mock(DataManager)
    LauncherCommandInputParameterDialogFactory inputParameterDialogFactory
    Dialogs.InputDialogBuilder inputDialog

    def setup() {
        inputParameterDialogFactory = Mock(LauncherCommandInputParameterDialogFactory)
        sut = new LauncherCommandExecutorAPIBean(
                dataManager: dataManager,
                launcherCommandExecutorFactory: executorFactory,
                launcherCommandInputParameterDialogFactory: inputParameterDialogFactory
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
        1 * executorFactory.create(launcherCommand) >> Mock(LauncherCommandExecutor)
    }

    def "launchCommands executes the launch command executor"() {
        given:
        def launcherCommand = new ScreenLauncherCommand()

        and:
        dataManager.reload(launcherCommand, 'launcherCommand-with-details') >> launcherCommand
        and:
        LauncherCommandExecutor executor = Mock(LauncherCommandExecutor)
        executorFactory.create(launcherCommand) >> executor
        when:
        sut.launchCommand(launcherCommand)
        then:
        1 * executor.execute(launcherCommand)
    }

    def "launchCommands opens the input dialog when a input parameter is specified"() {
        given:
        def launcherCommand = new ScreenLauncherCommand(
                inputParameters: [new InputParameter(
                        name: "string",
                        type: InputParameterType.STRING,
                        translations: [translation(Locale.ENGLISH, "my String")]
                )]
        )

        inputDialog = Mock(Dialogs.InputDialogBuilder)

        inputParameterDialogFactory.create(_, _) >> inputDialog

        and:
        dataManager.reload(launcherCommand, 'launcherCommand-with-details') >> launcherCommand
        and:
        LauncherCommandExecutor executor = Mock(LauncherCommandExecutor)
        executorFactory.create(launcherCommand) >> executor

        when:
        sut.launchCommand(launcherCommand)
        then:
        1 * inputDialog.show()
    }


    private InputParameterTranslation translation(Locale locale, String value) {
        new InputParameterTranslation(
                locale: locale,
                text: value
        )
    }
}
