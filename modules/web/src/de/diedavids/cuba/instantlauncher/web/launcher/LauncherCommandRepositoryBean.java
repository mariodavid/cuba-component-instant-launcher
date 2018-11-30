package de.diedavids.cuba.instantlauncher.web.launcher;

import com.haulmont.cuba.core.global.DataManager;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component(LauncherCommmandRepository.NAME)
public class LauncherCommandRepositoryBean implements LauncherCommmandRepository {

  @Inject
  private DataManager dataManager;

  @Override
  public List<LauncherCommandTranslation> findAllLauncherCommandTranslationByTextAndLocale(
      String query,
      Locale locale) {
    String queryString = "select e from ddcil$LauncherCommandTranslation e where e.text LIKE :query and e.locale = :locale";
    return dataManager.load(
        LauncherCommandTranslation.class)
        .query(queryString)
        .parameter("query", "(?i)%" + query + "%")
        .parameter("locale", locale)
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


}
