package de.diedavids.cuba.instantlauncher.web.components;

import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.strategy.DefaultSearchEntry;
import com.haulmont.addon.search.strategy.SearchEntry;
import com.haulmont.addon.search.strategy.SearchStrategy;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Security;
import com.haulmont.cuba.core.global.UserSessionSource;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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
    Security security;

    @Inject
    private DataManager dataManager;

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

        Locale currentLocale = null;

        if (userSessionSource.checkCurrentUserSession()) {
            currentLocale = userSessionSource.getLocale();
        }
        else {
            currentLocale = messages.getTools().getDefaultLocale();
        }


        List<LauncherCommandTranslation> matchingLauncherCommands = dataManager.load(
            LauncherCommandTranslation.class)
                .query("select e from ddcil$LauncherCommandTranslation e where e.text LIKE :query and e.locale = :locale")
                .parameter("query", "(?i)%" + query + "%")
            .parameter("locale", currentLocale)
                .list();

        return matchingLauncherCommands.stream()
                .map(launcherCommandTranslation -> createSearchEntry(query, launcherCommandTranslation))
                .collect(Collectors.toList());
    }

    private SearchEntry createSearchEntry(String query, LauncherCommandTranslation launcherCommandTranslation) {
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

        LauncherCommandTranslation launcherCommandTranslation = dataManager.load(LauncherCommandTranslation.class)
                .id(UUID.fromString(searchEntry.getId()))
                .view("launcherCommandTranslation-with-launcherCommand")
                .one();

        launcherCommandExecutorAPI.launchCommand(launcherCommandTranslation.getLauncherCommand());
    }


}
