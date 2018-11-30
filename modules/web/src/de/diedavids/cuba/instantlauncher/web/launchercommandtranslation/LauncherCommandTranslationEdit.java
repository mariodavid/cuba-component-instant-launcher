package de.diedavids.cuba.instantlauncher.web.launchercommandtranslation;

import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.FieldGroup.FieldConfig;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.inject.Inject;

public class LauncherCommandTranslationEdit extends AbstractEditor<LauncherCommandTranslation> {

  @Inject
  protected FieldGroup fieldGroup;

  @Inject
  protected ComponentsFactory componentsFactory;


  @Inject
  protected Datasource<LauncherCommandTranslation> launcherCommandTranslationDs;


  @Inject
  protected GlobalConfig globalConfig;

  @Override
  public void init(Map<String, Object> params) {
    super.init(params);

    FieldConfig fieldConfig = fieldGroup.getField("locale");
    LookupField localeField = componentsFactory.createComponent(LookupField.class);
    localeField.setOptionsMap(globalConfig.getAvailableLocales());
    localeField.setDatasource(launcherCommandTranslationDs, "locale");
    fieldConfig.setComponent(localeField);

  }

}