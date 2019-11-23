package de.diedavids.cuba.instantlauncher.web.launcher


import com.haulmont.cuba.core.global.MessageTools
import com.haulmont.cuba.core.global.UserSessionSource
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter as DialogInputParameter
import de.diedavids.cuba.instantlauncher.entity.InputParameter
import de.diedavids.cuba.instantlauncher.entity.InputParameterTranslation
import de.diedavids.cuba.instantlauncher.entity.InputParameterType
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import javax.inject.Inject

@Component('ddcil_LauncherCommandInputParameterFactory')
@Slf4j
class LauncherCommandInputParameterFactory {

  @Inject
  protected UserSessionSource userSessionSource
  @Inject
  protected MessageTools messageTools


  DialogInputParameter create(InputParameter inputParameter) {

    DialogInputParameter dialogInputParameter = createDialogInputParameterInstance(inputParameter)

    dialogInputParameter.withCaption(inputParameterCaption(inputParameter))
    dialogInputParameter.withRequired(inputParameter.required ?: false)

    return dialogInputParameter
  }

  private DialogInputParameter createDialogInputParameterInstance(InputParameter inputParameter) {
    switch (inputParameter.type) {
      case InputParameterType.STRING: return DialogInputParameter.stringParameter(inputParameter.name)
      case InputParameterType.INTEGER: return DialogInputParameter.intParameter(inputParameter.name)
      case InputParameterType.BOOLEAN: return DialogInputParameter.booleanParameter(inputParameter.name)
      case InputParameterType.DATE: return DialogInputParameter.dateParameter(inputParameter.name)
      case InputParameterType.LOCAL_DATE: return DialogInputParameter.localDateParameter(inputParameter.name)
      case InputParameterType.LOCAL_DATE_TIME: return DialogInputParameter.localDateTimeParameter(inputParameter.name)
      case InputParameterType.LOCAL_TIME: return DialogInputParameter.localTimeParameter(inputParameter.name)
      case InputParameterType.DATETIME: return DialogInputParameter.dateTimeParameter(inputParameter.name)
      case InputParameterType.DOUBLE: return DialogInputParameter.doubleParameter(inputParameter.name)
      case InputParameterType.BIG_DECIMAL: return DialogInputParameter.bigDecimalParameter(inputParameter.name)
      case InputParameterType.ENUMERATION: return DialogInputParameter.enumParameter(inputParameter.name, Class.forName(inputParameter.enumerationClass))
      case InputParameterType.ENTITY: return DialogInputParameter.entityParameter(inputParameter.name, inputParameter.entityClass.getJavaClass())
      default: return DialogInputParameter.parameter(inputParameter.name)
    }
  }


  private String inputParameterCaption(InputParameter inputParameter) {

    if (!inputParameter.translations) {
      return inputParameter.name
    }


    def inputParameterTranslation = findTranslationFor(inputParameter, getCurrentLocale())


    inputParameterTranslation ? inputParameterTranslation.text : inputParameter.name
  }

  private InputParameterTranslation findTranslationFor(InputParameter inputParameter, Locale locale) {
    def targetTranslation = inputParameter.translations.find { it.locale == locale }
    if (targetTranslation) {
      return targetTranslation
    }

    return inputParameter.translations.find { it.locale == messageTools.getDefaultLocale() }
  }


  private Locale getCurrentLocale() {

    if (userSessionSource.checkCurrentUserSession()) {
      return userSessionSource.getLocale();
    } else {
      return messageTools.getDefaultLocale();
    }
  }

}