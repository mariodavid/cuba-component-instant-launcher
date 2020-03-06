package de.diedavids.cuba.instantlauncher.web.launcher;

import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.KeyCombination;
import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.web.gui.components.util.ShortcutListenerDelegate;
import com.haulmont.cuba.web.widgets.CubaOrderedActionsLayout;
import com.vaadin.event.ShortcutListener;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandGroup;
import de.diedavids.cuba.instantlauncher.web.launcher.menu.MenuAdapter;
import de.diedavids.cuba.instantlauncher.web.launcher.menu.MenuItemAdapter;
import de.diedavids.cuba.instantlauncher.web.launcher.repository.LauncherCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

@Component("ddcil_LauncherCommandsInitializer")
public class LauncherCommandsInitializer {

  private static final Logger log = LoggerFactory.getLogger(LauncherCommandsInitializer.class);

  @Inject
  protected LauncherCommandExecutorAPI launcherCommandExecutorAPI;

  @Inject
  protected LauncherCommandRepository launcherCommandRepository;

  @Inject
  protected UserSessionSource userSessionSource;

  public void initKeyboardShortcuts(RootWindow topLevelWindow) {

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


  public void initMenuLauncherCommands(MenuAdapter menu) {

    List<LauncherCommand> mainMenuLauncherCommands = launcherCommandRepository.findAllMainMenuLauncherCommands("launcherCommand-with-details");

    mainMenuLauncherCommands
            .stream()
            .filter(launcherCommand -> launcherCommand.getGroup() != null)
            .collect(groupingBy(LauncherCommand::getGroup))
            .forEach((launcherCommandGroup, launcherCommands) -> {
              MenuItemAdapter groupItem = groupMenuItem(launcherCommandGroup, menu);
              addLauncherCommandsToMenu(launcherCommands, groupItem, menu);
              menu.addMenuItem(groupItem.getMenuItem(), 0);
            });

    mainMenuLauncherCommands
            .stream()
            .filter(launcherCommand -> launcherCommand.getGroup() == null)
            .forEach(launcherCommand -> addLauncherCommandToMenu(launcherCommand, null, menu));
  }

  private MenuItemAdapter groupMenuItem(LauncherCommandGroup launcherCommandGroup, MenuAdapter mainMenu) {
    return mainMenu.createMenuItem(
            launcherCommandGroup.getId().toString(),
            launcherCommandGroup.getName()
    );
  }

  private void addLauncherCommandsToMenu(
          List<LauncherCommand> launcherCommands,
          MenuItemAdapter parentMenuItem,
          MenuAdapter menu
  ) {
    launcherCommands
            .forEach(launcherCommand -> addLauncherCommandToMenu(launcherCommand, parentMenuItem, menu));
  }

  private void addLauncherCommandToMenu(
          LauncherCommand launcherCommand,
          MenuItemAdapter parentMenuItem,
          MenuAdapter mainMenu
  ) {
    MenuItemAdapter subItem = mainMenu.createMenuItem(
            launcherCommand.getName(),
            launcherCommand.translationForLocale(userSessionSource.getUserSession().getLocale()),
            null,
            menuItem -> launcherCommandExecutorAPI.launchCommand(launcherCommand));

    if (parentMenuItem != null) {
      parentMenuItem.addChildItem(subItem);
    }
    else {
      mainMenu.addMenuItem(subItem.getMenuItem(), 0);
    }
  }
}
