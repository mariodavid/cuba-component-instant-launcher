package de.diedavids.cuba.instantlauncher.web.screens.launchercommandtranslation;

import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.FieldGroup.FieldConfig;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;

import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;

@UiController("ddcil$LauncherCommandTranslation.edit")
@UiDescriptor("launcher-command-translation-edit.xml")
@EditedEntityContainer("launcherCommandTranslationDc")
@LoadDataBeforeShow
public class LauncherCommandTranslationEdit extends StandardEditor<LauncherCommandTranslation> {

  @Inject
  protected GlobalConfig globalConfig;
  @Inject
  protected LookupField<Locale> localeField;

  @Subscribe
  protected void onInit(InitEvent event) {
    localeField.setOptionsMap(
            globalConfig.getAvailableLocales()
    );
  }
}