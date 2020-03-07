package de.diedavids.cuba.instantlauncher.web.screens.launchercommandgrouptranslation;

import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandGroupTranslation;

import javax.inject.Inject;
import java.util.Locale;

@UiController("ddcil_LauncherCommandGroupTranslation.edit")
@UiDescriptor("launcher-command-group-translation-edit.xml")
@EditedEntityContainer("launcherCommandGroupTranslationDc")
@LoadDataBeforeShow
public class LauncherCommandGroupTranslationEdit extends StandardEditor<LauncherCommandGroupTranslation> {

    @Inject
    protected LookupField<Locale> localeField;

    @Inject
    protected GlobalConfig globalConfig;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        localeField.setOptionsMap(globalConfig.getAvailableLocales());
    }

}