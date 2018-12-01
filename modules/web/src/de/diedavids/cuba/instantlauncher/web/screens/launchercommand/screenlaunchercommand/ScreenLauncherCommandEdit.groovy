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
  protected FieldGroup screenFieldGroup;
  @Inject
  protected ComponentsFactory componentsFactory;

  @Inject
  protected Datasource<ScreenLauncherCommand> launcherCommandDs;

  @Inject
  MetadataSelector metadataSelector

  @Named("screenFieldGroup.screenLauncherCommandType")
  LookupField screenTypeLookupField


  @Override
  public void init(Map<String, Object> params) {
    super.init(params);

    initScreenFilter();

    initScreenEntity();

  }


  private void initScreenEntity() {
    FieldConfig screenEntityFieldConfig = screenFieldGroup.getField("screenEntity");

    LookupField lookupField = componentsFactory.createComponent(LookupField)
    lookupField.optionsMap = metadataSelector.entitiesLookupFieldOptions
    lookupField.nullOptionVisible = false
    lookupField.setDatasource(launcherCommandDs, "screenEntity")


    screenEntityFieldConfig.setComponent(lookupField)


    screenEntityFieldConfig.visible = false


    screenTypeLookupField.addValueChangeListener(new ValueChangeListener() {
      @Override
      void valueChanged(ValueChangeEvent e) {

        if (e.value == ScreenLauncherCommandType.EDITOR) {
          screenEntityFieldConfig.visible = true
        }
        else {
          screenEntityFieldConfig.visible = false
        }
      }
    })
  }

  protected void initScreenFilter() {

    FieldConfig fieldConfig = screenFieldGroup.getField("screenId");
    LookupField screenField = componentsFactory.createComponent(LookupField.class);

    screenField.setOptionsMap(metadataSelector.sceenLookupFieldOptions);
    screenField.setDatasource(launcherCommandDs, "screenId");
    fieldConfig.setComponent(screenField);
  }

  @Override
  protected boolean preCommit() {

    if (item.screenLauncherCommandType == ScreenLauncherCommandType.EDITOR && !item.screenEntity) {
      showNotification("Screen Entity is required for Screen Type: Editor", NotificationType.TRAY)
      return false
    }
    else {
      return super.preCommit()
    }

  }
}