package de.diedavids.cuba.instantlauncher.web.launchercommand;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager.OpenType;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Action.BeforeActionPerformedHandler;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import de.diedavids.cuba.instantlauncher.entity.BeanLauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;

public class LauncherCommandBrowse extends AbstractLookup {

  @Named("launcherCommandsTable.edit")
  protected EditAction launcherCommandsTableEdit;

  @Inject
  protected GroupTable<LauncherCommand> launcherCommandsTable;

  @Inject
  protected GroupDatasource<LauncherCommand, UUID> launcherCommandsDs;
  @Inject
  protected Metadata metadata;

  public void createScreen() {
    openLauncherScreen(metadata.create(ScreenLauncherCommand.class));
  }

  public void createScript() {
    openLauncherScreen(metadata.create(ScriptLauncherCommand.class));
  }
  public void createBean() {
    openLauncherScreen(metadata.create(BeanLauncherCommand.class));
  }

  private void openLauncherScreen(LauncherCommand entity) {
    openEditor(entity, OpenType.THIS_TAB)
        .addCloseWithCommitListener(() -> launcherCommandsDs.refresh());
  }


  @Override
  public void init(Map<String, Object> params) {
    super.init(params);

    launcherCommandsTableEdit.setBeforeActionPerformedHandler(() -> {
      LauncherCommand command = launcherCommandsTable.getSingleSelected();

      if (command instanceof ScreenLauncherCommand) {
        launcherCommandsTableEdit.setWindowId("ddcil$ScreenLauncherCommand.edit");
      }
      else if (command instanceof ScriptLauncherCommand) {
        launcherCommandsTableEdit.setWindowId("ddcil$ScriptLauncherCommand.edit");
      }
      else if (command instanceof BeanLauncherCommand) {
        launcherCommandsTableEdit.setWindowId("ddcil$BeanLauncherCommand.edit");
      }
      return true;
    });
  }

}