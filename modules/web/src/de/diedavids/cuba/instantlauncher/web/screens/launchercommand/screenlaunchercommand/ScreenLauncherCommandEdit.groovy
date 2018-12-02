package de.diedavids.cuba.instantlauncher.web.screens.launchercommand.screenlaunchercommand

import com.haulmont.cuba.gui.components.AbstractEditor
import com.haulmont.cuba.gui.components.Component.ValueChangeEvent
import com.haulmont.cuba.gui.components.Component.ValueChangeListener
import com.haulmont.cuba.gui.components.FieldGroup
import com.haulmont.cuba.gui.components.FieldGroup.FieldConfig
import com.haulmont.cuba.gui.components.LookupField
import com.haulmont.cuba.gui.data.Datasource
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommandType

import javax.inject.Inject
import javax.inject.Named

class ScreenLauncherCommandEdit extends AbstractEditor<ScreenLauncherCommand> {

  @Inject
  protected FieldGroup screenFieldGroup
  @Inject
  protected ComponentsFactory componentsFactory

  @Inject
  protected Datasource<ScreenLauncherCommand> launcherCommandDs

  @Inject
  MetadataSelector metadataSelector

  @Named('screenFieldGroup.screenLauncherCommandType')
  LookupField screenTypeLookupField

  private final String SCREEN_ENTITIY_ATTRIBUTE = 'screenEntity'
  private final String SCREEN_ENTITY_ATTRIBUTE = 'screenId'

  @Override
  void init(Map<String, Object> params) {
    super.init(params)

    initScreenIdLookupField()
    initScreenEntityLookupField()
  }

  private void initScreenEntityLookupField() {
    FieldConfig screenEntityFieldConfig = screenFieldGroup.getField(SCREEN_ENTITIY_ATTRIBUTE)

    LookupField lookupField = componentsFactory.createComponent(LookupField)
    lookupField.optionsMap = metadataSelector.entitiesLookupFieldOptions
    lookupField.setDatasource(launcherCommandDs, SCREEN_ENTITIY_ATTRIBUTE)
    screenEntityFieldConfig.setComponent(lookupField)
    screenEntityFieldConfig.visible = false

    registerVisibilityChangeForEntityScreen(screenEntityFieldConfig)
  }

  protected registerVisibilityChangeForEntityScreen(screenEntityFieldConfig) {
    screenTypeLookupField.addValueChangeListener(new ValueChangeListener() {
      @Override
      void valueChanged(ValueChangeEvent e) {
        screenEntityFieldConfig.visible = e.value == ScreenLauncherCommandType.EDITOR
      }
    })
  }

  protected void initScreenIdLookupField() {

    FieldConfig fieldConfig = screenFieldGroup.getField(SCREEN_ENTITY_ATTRIBUTE)
    LookupField screenField = componentsFactory.createComponent(LookupField)

    screenField.setOptionsMap(metadataSelector.sceenLookupFieldOptions)
    screenField.setDatasource(launcherCommandDs, SCREEN_ENTITY_ATTRIBUTE)
    fieldConfig.setComponent(screenField)
  }

  @Override
  protected boolean preCommit() {

    if (item.screenLauncherCommandType == ScreenLauncherCommandType.EDITOR && !item.screenEntity) {
      showNotification('Screen Entity is required for Screen Type: Editor', NotificationType.TRAY)
      return false
    }

    super.preCommit()
  }
}