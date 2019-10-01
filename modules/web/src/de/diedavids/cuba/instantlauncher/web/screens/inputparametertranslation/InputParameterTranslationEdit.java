package de.diedavids.cuba.instantlauncher.web.screens.inputparametertranslation;

import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.InputParameterTranslation;

import javax.inject.Inject;
import java.util.Locale;

@UiController("ddcil_InputParameterTranslation.edit")
@UiDescriptor("input-parameter-translation-edit.xml")
@EditedEntityContainer("inputParameterTranslationDc")
@LoadDataBeforeShow
public class InputParameterTranslationEdit extends StandardEditor<InputParameterTranslation> {

    @Inject
    protected LookupField<Locale> localeField;

    @Inject
    protected GlobalConfig globalConfig;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        localeField.setOptionsMap(globalConfig.getAvailableLocales());
    }
    
    
}