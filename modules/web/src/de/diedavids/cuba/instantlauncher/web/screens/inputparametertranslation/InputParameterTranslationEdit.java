package de.diedavids.cuba.instantlauncher.web.screens.inputparametertranslation;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.InputParameterTranslation;

@UiController("ddcil_InputParameterTranslation.edit")
@UiDescriptor("input-parameter-translation-edit.xml")
@EditedEntityContainer("inputParameterTranslationDc")
@LoadDataBeforeShow
public class InputParameterTranslationEdit extends StandardEditor<InputParameterTranslation> {
}