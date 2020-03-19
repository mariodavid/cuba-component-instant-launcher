package de.diedavids.cuba.instantlauncher.web.launcher;

import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.KeyCombination;
import com.haulmont.cuba.gui.components.RootWindow;
import com.haulmont.cuba.web.gui.components.util.ShortcutListenerDelegate;
import com.haulmont.cuba.web.widgets.CubaOrderedActionsLayout;
import com.vaadin.event.ShortcutListener;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandGroup;
import de.diedavids.cuba.instantlauncher.service.LauncherCommandService;
import de.diedavids.cuba.instantlauncher.web.launcher.menu.MenuAdapter;
import de.diedavids.cuba.instantlauncher.web.launcher.menu.MenuItemAdapter;
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
  protected LauncherCommandService launcherCommandService;
  @Inject
  protected UserSessionSource userSessionSource;

  public void initKeyboardShortcuts(RootWindow topLevelWindow) {

    CubaOrderedActionsLayout actionsLayout = topLevelWindow.unwrap(CubaOrderedActionsLayout.class);

    launcherCommandService.findAllLauncherCommandsWithShortcuts()
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

    List<LauncherCommand> mainMenuLauncherCommands = launcherCommandService.findAllMainMenuLauncherCommands("launcherCommand-with-details");

    List<LauncherCommandGroup> allMainMenuGroups = launcherCommandService.findAllMainMenuGroups("group-with-translations-view");


    mainMenuLauncherCommands
            .stream()
            .filter(launcherCommand -> launcherCommand.getGroup() != null)
            .filter(launcherCommand -> Boolean.TRUE.equals(launcherCommand.getGroup().getShowInMainMenu()))
            .collect(groupingBy(LauncherCommand::getGroup))
            .forEach((launcherCommandGroup, launcherCommands) -> {
              String groupCaption = groupCaption(allMainMenuGroups, launcherCommandGroup);
              MenuItemAdapter groupItem = groupMenuItem(launcherCommandGroup, groupCaption, menu);
              addLauncherCommandsToMenu(launcherCommands, groupItem, menu);
              menu.addMenuItem(groupItem.getMenuItem(), 0);
            });

    mainMenuLauncherCommands
            .stream()
            .filter(launcherCommand -> launcherCommand.getGroup() == null)
            .forEach(launcherCommand -> addLauncherCommandToMenu(launcherCommand, null, menu));
  }

  private String groupCaption(List<LauncherCommandGroup> allMainMenuGroups, LauncherCommandGroup groupToDisplay) {
    return allMainMenuGroups.stream()
            .filter(launcherCommandGroup -> launcherCommandGroup.equals(groupToDisplay))
            .findFirst()
            .map(launcherCommandGroup -> launcherCommandGroup.translationForLocale(userSessionSource.getLocale()))
            .orElse(groupToDisplay.getName());
  }

  private MenuItemAdapter groupMenuItem(LauncherCommandGroup launcherCommandGroup, String groupCaption, MenuAdapter mainMenu) {
    return mainMenu.createMenuItem(
            launcherCommandGroup.getId().toString(),
            groupCaption
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
            launcherCommand.translationForLocale(userSessionSource.getLocale()),
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
