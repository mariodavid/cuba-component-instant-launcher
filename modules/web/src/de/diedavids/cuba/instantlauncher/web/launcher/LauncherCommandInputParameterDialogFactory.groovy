package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.gui.Dialogs
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter
import com.haulmont.cuba.gui.screen.FrameOwner
import com.haulmont.cuba.gui.screen.UiControllerUtils
import com.haulmont.cuba.web.AppUI
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import javax.inject.Inject
import java.util.function.Consumer

@Slf4j
@Component('ddcil_LauncherCommandInputParameterDialogFactory')
class LauncherCommandInputParameterDialogFactory {

  @Inject
  protected LauncherCommandInputParameterFactory launcherCommandInputParameterFactory

  @Inject
  protected Messages messages

  Dialogs.InputDialogBuilder create(
          List<de.diedavids.cuba.instantlauncher.entity.InputParameter> inputParameters,
          Consumer<Map<String, Object>> inputParametersEnteredHandler
  ) {

    List<InputParameter> inputParameterObjects = transformPersistentInputParametersToInputParameters(inputParameters)

    def inputDialogBuilder = dialogs.createInputDialog(getFrameOwner())
    inputDialogBuilder
            .withCaption(inputParameterDialogTitle())
            .withActions(DialogActions.OK_CANCEL)
            .withCloseListener({ InputDialog.InputDialogCloseEvent closeEvent ->
              if (okWasClicked(closeEvent)) {
                inputParametersEnteredHandler.accept(inputValues(inputParameterObjects, closeEvent))
              }
            } as Consumer)
    inputParameterObjects.each {
      inputDialogBuilder.withParameter(it)
    }
    inputDialogBuilder
  }

  private String inputParameterDialogTitle() {
    messages.getMessage(this.getClass(), "inputParametersDialogTitle")
  }

  private boolean okWasClicked(InputDialog.InputDialogCloseEvent closeEvent) {
    closeEvent.getCloseAction() == InputDialog.INPUT_DIALOG_OK_ACTION
  }

  private Map<String, Object> inputValues(List<InputParameter> inputParameters, InputDialog.InputDialogCloseEvent closeEvent) {
    def map = inputParameters.collectEntries {
      [(it.id): null]
    } as Map<String, Object>

    map + closeEvent.getValues()
  }

  private List<InputParameter> transformPersistentInputParametersToInputParameters(
          List<de.diedavids.cuba.instantlauncher.entity.InputParameter> inputParameters
  ) {
    inputParameters.collect { inputParameter ->
      launcherCommandInputParameterFactory.create(inputParameter)
    }
  }


  protected Dialogs getDialogs() {
    UiControllerUtils.getScreenContext(getFrameOwner()).dialogs
  }

  protected FrameOwner getFrameOwner() {
    AppUI.current.topLevelWindow.getFrameOwner()
  }

}