package de.diedavids.cuba.instantlauncher.web.launcher;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.KeyCombination;
import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.web.gui.components.util.ShortcutListenerDelegate;
import com.haulmont.cuba.web.widgets.CubaOrderedActionsLayout;
import com.vaadin.event.ShortcutListener;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.web.launcher.repository.LauncherCommandRepository;
import de.diedavids.cuba.instantlauncher.web.screens.main.InstantLauncherAppMainWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("ddcil_LauncherCommandShortcutInitializer")
public class LauncherCommandShortcutInitializer {

  private static final Logger log = LoggerFactory.getLogger(LauncherCommandShortcutInitializer.class);

  @Inject
  protected LauncherCommandExecutorAPI launcherCommandExecutorAPI;

  @Inject
  protected LauncherCommandRepository launcherCommandRepository;

  public void initInstantLauncherShortcuts(RootWindow topLevelWindow) {

    CubaOrderedActionsLayout actionsLayout = topLevelWindow.unwrap(CubaOrderedActionsLayout.class);

    launcherCommandRepository.findAllLauncherCommandsWithShortcuts()
            .stream()
            .filter(this::hasValidShortcut)
            .forEach(launcherCommand -> actionsLayout.addShortcutListener(createShortcutFor(launcherCommand)));
  }

  private boolean hasValidShortcut(LauncherCommand launcherCommand) {
    try {
      createKeyCombination(launcherCommand);
      return true;

    } catch (IllegalArgumentException e) {
      log.warn("Shortcut ({}) for Launcher Command: {} not valid. Will be ignored.", launcherCommand.getShortcut(), launcherCommand.getName());
      return false;
    }
  }

  private KeyCombination createKeyCombination(LauncherCommand launcherCommand) {
    return KeyCombination.create(launcherCommand.getShortcut());
  }


  protected ShortcutListener createShortcutFor(LauncherCommand launcherCommand) {
    KeyCombination combination = createKeyCombination(launcherCommand);

    return new ShortcutListenerDelegate(
            launcherCommand.getName(),
            combination.getKey().getCode(),
            KeyCombination.Modifier.codes(combination.getModifiers())
    ).withHandler((sender, target) -> executeLauncherCommand(sender, target, launcherCommand));

  }

  protected void executeLauncherCommand(Object sender, Object target, LauncherCommand launcherCommand) {
    launcherCommandExecutorAPI.launchCommand(launcherCommand);
  }

}
