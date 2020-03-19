package de.diedavids.cuba.instantlauncher.repository;

import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandGroup;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface LauncherCommandRepository {

  String NAME = "ddcil_LauncherCommandRepositoryBean";

  List<LauncherCommandTranslation> findAllLauncherCommandTranslationByTextAndLocale(
          String query,
          Locale locale
  );


  List<LauncherCommand> findAllLauncherCommandsByName(String query);

  LauncherCommand findLauncherCommandByTranslationId(UUID id);

  List<LauncherCommand> findAllLauncherCommandsWithShortcuts();

  List<LauncherCommand> findAllMainMenuLauncherCommands(String view);

  LauncherCommand findLauncherCommandById(UUID launcherCommandId);

  List<LauncherCommandGroup> findAllMainMenuGroups(String view);
}
