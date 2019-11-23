package de.diedavids.cuba.instantlauncher.web.screens.inputparameter;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.InputParameter;
import de.diedavids.cuba.instantlauncher.entity.InputParameterType;
import de.diedavids.cuba.metadataextensions.dataprovider.EntityDataProvider;

import javax.inject.Inject;
import java.util.stream.Collectors;

@UiController("ddcil_UserInputParameter.edit")
@UiDescriptor("input-parameter-edit.xml")
@EditedEntityContainer("inputParameterDc")
@LoadDataBeforeShow
public class InputParameterEdit extends StandardEditor<InputParameter> {

    @Inject
    protected LookupField<MetaClass> entityClassField;
    @Inject
    protected LookupField<String> enumerationClassField;
    @Inject
    protected EntityDataProvider entityDataProvider;
    @Inject
    protected Metadata metadata;
    @Inject
    protected Messages messages;

    @Subscribe
    protected void onInit(InitEvent event) {
        entityClassField.setOptionsMap(
                entityDataProvider.entitiesLookupFieldOptions()
        );

        enumerationClassField.setOptionsMap(
                metadata.getTools().getAllEnums().stream()

                    .collect(Collectors.toMap(
                            p -> messages.getMessage(p, p.getSimpleName()),
                            p ->p.getName()
                    ))
        );
    }

    @Subscribe("typeField")
    protected void onTypeFieldValueChange(HasValue.ValueChangeEvent<InputParameterType> event) {

        if (event.getValue() != null) {
            if (event.getValue().equals(InputParameterType.ENUMERATION)) {
                showEnumeration();
                hideEntity();
            }
            else if (event.getValue().equals(InputParameterType.ENTITY)) {
                showEntity();
                hideEnumeration();
            }
            else {
                hideAll();
            }
        }
        else {
            hideAll();
        }
    }

    private void showEntity() {
        entityClassField.setVisible(true);
    }

    private void showEnumeration() {
        enumerationClassField.setVisible(true);
    }

    private void hideAll() {
        hideEntity();
        hideEnumeration();
    }

    private void hideEntity() {
        entityClassField.setVisible(false);
        getEditedEntity().setEntityClass(null);
    }

    private void hideEnumeration() {
        enumerationClassField.setVisible(false);
        getEditedEntity().setEnumerationClass(null);
    }

}