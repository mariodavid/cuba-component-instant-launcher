package de.diedavids.cuba.instantlauncher.web.launcher.repository;

import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface LauncherCommandRepository {

  String NAME = "ddcil_LauncherCommandRepositoryBean";

  List<LauncherCommandTranslation> findAllLauncherCommandTranslationByTextAndLocale(String query,
      Locale locale);

  LauncherCommand findLauncherCommandByTranslationId(UUID id);

  List<LauncherCommand> findAllLauncherCommandsWithShortcuts();
}
