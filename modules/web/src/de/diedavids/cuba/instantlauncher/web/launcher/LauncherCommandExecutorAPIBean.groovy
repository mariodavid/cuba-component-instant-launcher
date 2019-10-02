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
  protected LauncherCommandInputParameterFactory launcherCommandInputParameterFactory


  void launchCommand(LauncherCommand launcherCommand) {

    LauncherCommand reloadedLauncherCommand = dataManager.reload(launcherCommand, 'launcherCommand-with-details')



    def executor = launcherCommandExecutorFactory.create(reloadedLauncherCommand)

    def inputParameters = reloadedLauncherCommand.getInputParameters();

    List<InputParameter> inputParameterObjects = transformPersistentInputParametersToInputParameters(inputParameters)

    if (inputParameterObjects) {
      Dialogs.InputDialogBuilder inputDialogBuilder = createInputDialogBuilder(executor, reloadedLauncherCommand, inputParameterObjects)
      inputDialogBuilder.show();
    }
    else {
      executor.execute(reloadedLauncherCommand)
    }


  }

  private Dialogs.InputDialogBuilder createInputDialogBuilder(executor, reloadedLauncherCommand, List<InputParameter> inputParameterObjects) {
    def inputDialogBuilder = dialogs.createInputDialog(frameOwner())
    inputDialogBuilder
            .withCaption("Input Parameters")
            .withActions(DialogActions.OK_CANCEL)
            .withCloseListener(new Consumer<InputDialog.InputDialogCloseEvent>() {
              @Override
              void accept(InputDialog.InputDialogCloseEvent closeEvent) {
                if (closeEvent.getCloseAction().equals(InputDialog.INPUT_DIALOG_OK_ACTION)) {
                  executor.execute(reloadedLauncherCommand, closeEvent.getValues())
                }
              }
            })

    inputParameterObjects.each {
      inputDialogBuilder.withParameter(it)
    }
    inputDialogBuilder
  }

  private List<InputParameter> transformPersistentInputParametersToInputParameters(List<de.diedavids.cuba.instantlauncher.entity.InputParameter> inputParameters) {
    List<InputParameter> inputParameterObjects = inputParameters.collect { inputParameter ->
      launcherCommandInputParameterFactory.create(inputParameter)
    } as List<InputParameter>
    inputParameterObjects
  }

  private Dialogs getDialogs() {
    UiControllerUtils.getScreenContext(frameOwner()).dialogs
  }

  private FrameOwner frameOwner() {
    AppUI.current.topLevelWindow.getFrameOwner()
  }
}
