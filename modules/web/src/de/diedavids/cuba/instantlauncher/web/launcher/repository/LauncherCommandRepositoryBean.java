package de.diedavids.cuba.instantlauncher.web.launcher.repository;

import com.haulmont.cuba.core.global.DataManager;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component(LauncherCommandRepository.NAME)
public class LauncherCommandRepositoryBean implements LauncherCommandRepository {

  @Inject
  private DataManager dataManager;

  @Override
  public List<LauncherCommandTranslation> findAllLauncherCommandTranslationByTextAndLocale(
      String query,
      Locale locale
  ) {
    String queryString = "select e from ddcil$LauncherCommandTranslation e where e.text LIKE :query and e.locale = :locale";
    return dataManager.load(LauncherCommandTranslation.class)
        .query(queryString)
        .parameter("query", "(?i)%" + query + "%")
        .parameter("locale", locale)
            .view("launcherCommandTranslation-with-launcherCommand")
        .list();
  }

  @Override
  public List<LauncherCommand> findAllLauncherCommandsByName(String query) {
    String queryString = "select e from ddcil$LauncherCommand e where e.name LIKE :query";
    return dataManager.load(LauncherCommand.class)
            .query(queryString)
            .parameter("query", "(?i)%" + query + "%")
            .list();
  }

  @Override
  public LauncherCommand findLauncherCommandByTranslationId(UUID launcherCommandTranslationId) {
    LauncherCommandTranslation launcherCommandTranslation = dataManager
        .load(LauncherCommandTranslation.class)
        .id(launcherCommandTranslationId)
        .view("launcherCommandTranslation-with-launcherCommand")
        .one();
    return launcherCommandTranslation.getLauncherCommand();
  }

  @Override
  public List<LauncherCommand> findAllLauncherCommandsWithShortcuts() {
    return dataManager.load(LauncherCommand.class)
            .query("select e from ddcil$LauncherCommand e where e.shortcut is not null")
            .list();
  }

  @Override
  public List<LauncherCommand> findAllMainMenuLauncherCommands(String view) {
    return dataManager.load(LauncherCommand.class)
            .query("select e from ddcil$LauncherCommand e where e.showInMainMenu = true")
            .view(view)
            .list();
  }

  @Override
  public LauncherCommand findLauncherCommandById(UUID launcherCommandId) {
    return dataManager.load(LauncherCommand.class)
            .id(launcherCommandId)
            .one();
  }


}
