package de.diedavids.cuba.instantlauncher.web.launcher


import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.gui.Dialogs
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter
import com.haulmont.cuba.gui.screen.FrameOwner
import com.haulmont.cuba.gui.screen.UiControllerUtils
import com.haulmont.cuba.web.AppUI
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import javax.inject.Inject
import java.util.function.Consumer

@Component(LauncherCommandExecutorAPI.NAME)
@Slf4j
class LauncherCommandExecutorAPIBean implements LauncherCommandExecutorAPI {

    @Inject
    DataManager dataManager

    @Inject
    LauncherCommandExecutorFactory launcherCommandExecutorFactory

    @Inject
    LauncherCommandInputParameterDialogFactory launcherCommandInputParameterDialogFactory

    void launchCommand(LauncherCommand launcherCommand) {

        LauncherCommand reloadedLauncherCommand = dataManager.reload(launcherCommand, 'launcherCommand-with-details')

        def executor = launcherCommandExecutorFactory.create(reloadedLauncherCommand)

        def inputParameters = reloadedLauncherCommand.getInputParameters();

        if (inputParameters) {
            Dialogs.InputDialogBuilder inputDialogBuilder = launcherCommandInputParameterDialogFactory
                    .create(
                            inputParameters,
                            new Consumer<Map<String, Object>>() {
                                @Override
                                void accept(Map<String, Object> inputParameterValues) {
                                    executor.execute(reloadedLauncherCommand, inputParameterValues)
                                }
                            }
                    )
            inputDialogBuilder.show();
        } else {
            executor.execute(reloadedLauncherCommand)
        }
    }
}
