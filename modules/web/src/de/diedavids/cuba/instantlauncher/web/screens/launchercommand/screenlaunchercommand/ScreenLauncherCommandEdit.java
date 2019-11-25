package de.diedavids.cuba.instantlauncher.web.screens.launchercommand.screenlaunchercommand;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommandType;

import javax.inject.Inject;

@UiController("ddcil$ScreenLauncherCommand.edit")
@UiDescriptor("screen-launcher-command-edit.xml")
@EditedEntityContainer("screenLauncherCommandDc")
@LoadDataBeforeShow
public class ScreenLauncherCommandEdit extends StandardEditor<ScreenLauncherCommand> {

  @Inject
  protected LookupField<String> screenIdField;
  @Inject
  protected LookupField<String> screenEntityField;

  @Inject
  protected LookupField<ScreenLauncherCommandType> screenLauncherCommandTypeField;

  @Inject
  protected MetadataSelector metadataSelector;

  @Subscribe
  protected void onInit(InitEvent event) {

    screenIdField.setOptionsMap(
            metadataSelector.getScreenLookupFieldOptions()
    );

    screenEntityField.setOptionsMap(
            metadataSelector.getEntitiesLookupFieldOptions()
    );

  }

  @Subscribe("screenLauncherCommandTypeField")
  protected void onScreenLauncherCommandTypeFieldValueChange(HasValue.ValueChangeEvent<ScreenLauncherCommandType> event) {

    if (event.getValue() != null) {

      if (event.getValue().equals(ScreenLauncherCommandType.EDITOR)) {
        showScreenEntityField();
      }
      else {
        hideScreenEntityField();
      }
    }
    else {
      hideScreenEntityField();
    }
  }

  private void showScreenEntityField() {
    screenEntityField.setVisible(true);
    screenEntityField.setRequired(true);
  }

  private void hideScreenEntityField() {
    screenEntityField.setVisible(false);
    screenEntityField.setRequired(false);
  }


}
