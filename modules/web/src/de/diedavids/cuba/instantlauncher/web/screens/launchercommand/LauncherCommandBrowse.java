package de.diedavids.cuba.instantlauncher.web.screens.launchercommand;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.RadioButtonGroup;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.instantlauncher.entity.*;

import javax.inject.Inject;

@UiController("ddcil$LauncherCommand.browse")
@UiDescriptor("launcher-command-browse.xml")
@LookupComponent("launcherCommandsTable")
@LoadDataBeforeShow
public class LauncherCommandBrowse extends StandardLookup<LauncherCommand> {

    @Inject
    protected Dialogs dialogs;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected GroupTable<LauncherCommand> launcherCommandsTable;
    @Inject
    protected Metadata metadata;

    @Subscribe("launcherCommandsTable.create")
    protected void onLauncherCommandsTableCreate(Action.ActionPerformedEvent event) {

        dialogs.createInputDialog(this)
                .withCaption(messageBundle.getMessage("selectLauncherCommandType"))
                .withParameter(
                        InputParameter.parameter("launcherCommandType")
                        .withField(() -> {
                            RadioButtonGroup field = uiComponents.create(RadioButtonGroup.class);
                            field.setOptionsEnum(LauncherCommandType.class);
                            field.setRequired(true);
                            return field;
                        })
                )
                .withCloseListener(closeEvent -> {
                    if (closeEvent.getCloseAction().equals(InputDialog.INPUT_DIALOG_OK_ACTION)) {
                        LauncherCommandType launcherCommandType = closeEvent.getValue("launcherCommandType");

                        screenBuilders.editor(launcherCommandsTable)
                                .newEntity(getLauncherCommandType(launcherCommandType))
                                .show();
                    }
                })
                .show();
    }

    private LauncherCommand getLauncherCommandType(LauncherCommandType launcherCommandType) {
        switch (launcherCommandType) {
            case SCRIPT_LAUNCHER:
                return metadata.create(ScriptLauncherCommand.class);
            case BEAN_LAUNCHER:
                return metadata.create(BeanLauncherCommand.class);
            case SCREEN_LAUNCHER:
                return metadata.create(ScreenLauncherCommand.class);
            default:
                return null;
        }
    }


}