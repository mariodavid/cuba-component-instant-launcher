package de.diedavids.cuba.instantlauncher.web.screens.inputparameter;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.InputParameter;

@UiController("ddcil_UserInputParameter.edit")
@UiDescriptor("input-parameter-edit.xml")
@EditedEntityContainer("inputParameterDc")
@LoadDataBeforeShow
public class InputParameterEdit extends StandardEditor<InputParameter> {
}