package de.diedavids.cuba.instantlauncher.web.launcher;

import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.strategy.DefaultSearchEntry;
import com.haulmont.addon.search.strategy.SearchEntry;
import com.haulmont.addon.search.strategy.SearchStrategy;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.UserSessionSource;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;
import de.diedavids.cuba.instantlauncher.web.launcher.repository.LauncherCommandRepository;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component("ddcil_launcherCommandSearchStrategy")
public class LauncherCommandSearchStrategy implements SearchStrategy {


  @Nonnull
  @Override
  public String name() {
    return "instantLauncherSearchStrategy";
  }


  @Inject
  LauncherCommandExecutorAPI launcherCommandExecutorAPI;

  @Inject
  protected LauncherCommandRepository launcherCommandRepository;

  @Inject
  protected UserSessionSource userSessionSource;


  @Inject
  protected Messages messages;

  @Nonnull
  @Override
  public List<SearchEntry> load(@Nonnull SearchContext context, String query) {
    return findMatchingLaunchers(query);
  }

  private List<SearchEntry> findMatchingLaunchers(String query) {

    Locale currentLocale = getCurrentLocale();

    List<LauncherCommandTranslation> matchingLauncherCommands = findMatchingLauncherCommands(
        query, currentLocale);

    return toSearchEntries(query, matchingLauncherCommands);
  }

  private List<SearchEntry> toSearchEntries(String query,
      List<LauncherCommandTranslation> matchingLauncherCommands) {
    return matchingLauncherCommands.stream()
        .map(launcherCommandTranslation -> createSearchEntry(query, launcherCommandTranslation))
        .collect(Collectors.toList());
  }

  private List<LauncherCommandTranslation> findMatchingLauncherCommands(String query,
      Locale currentLocale) {
    return launcherCommandRepository.findAllLauncherCommandTranslationByTextAndLocale(query, currentLocale);
  }

  private Locale getCurrentLocale() {

    if (userSessionSource.checkCurrentUserSession()) {
      return userSessionSource.getLocale();
    } else {
      return messages.getTools().getDefaultLocale();
    }
  }

  private SearchEntry createSearchEntry(String query,
      LauncherCommandTranslation launcherCommandTranslation) {
    return new DefaultSearchEntry(
        launcherCommandTranslation.getId().toString(),
        query,
        launcherCommandTranslation.getText(),
        name()
    );
  }

  @Override
  public void invoke(@Nonnull SearchContext context, SearchEntry value) {
    DefaultSearchEntry searchEntry = (DefaultSearchEntry) value;

    LauncherCommand launcherCommand = findLauncherCommandByTranslationId(
        searchEntry.getId());

    launcherCommandExecutorAPI.launchCommand(launcherCommand);
  }

  private LauncherCommand findLauncherCommandByTranslationId(String launchCommandTranslationId) {
    return launcherCommandRepository.findLauncherCommandByTranslationId(UUID.fromString(launchCommandTranslationId));
  }


}
