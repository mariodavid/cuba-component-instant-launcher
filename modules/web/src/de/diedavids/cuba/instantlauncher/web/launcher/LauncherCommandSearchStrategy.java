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
import java.util.stream.Stream;
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


        List<LauncherCommandTranslation> launcherCommandsByTranslation = findLauncherCommandsByTranslation(
                query,
                currentLocale
        );



        List<LauncherCommand> launcherCommandsByName = findLauncherCommandsByName(
                query
        );





        return toSearchEntries(query, launcherCommandsByTranslation, launcherCommandsByName);

    }

    private List<SearchEntry> toSearchEntries(String query,
                                              List<LauncherCommandTranslation> matchingLauncherCommands,
                                              List<LauncherCommand> launcherCommandsByName) {
        Stream<SearchEntry> launcherCommandsByTranslationSearchEntries = matchingLauncherCommands.stream()
                .map(launcherCommandTranslation -> createSearchEntry(query, launcherCommandTranslation));

        Stream<SearchEntry> launcherCommandsByNameSearchEntries = launcherCommandsByName.stream()
                .filter(launcherCommand -> matchingLauncherCommands.stream().noneMatch(launcherCommandTranslation -> launcherCommandTranslation.getLauncherCommand().equals(launcherCommand)))
                .map(launcherCommand -> createSearchEntry(query, launcherCommand));

        return Stream.concat(
                launcherCommandsByTranslationSearchEntries,
                launcherCommandsByNameSearchEntries
        )
                .collect(Collectors.toList());

    }

    private List<LauncherCommandTranslation> findLauncherCommandsByTranslation(
            String query,
            Locale currentLocale
    ) {
        return launcherCommandRepository.findAllLauncherCommandTranslationByTextAndLocale(query, currentLocale);
    }

    private List<LauncherCommand> findLauncherCommandsByName(
            String query
    ) {
        return launcherCommandRepository.findAllLauncherCommandsByName(query);
    }

    private Locale getCurrentLocale() {

        if (userSessionSource.checkCurrentUserSession()) {
            return userSessionSource.getLocale();
        } else {
            return messages.getTools().getDefaultLocale();
        }
    }

    private SearchEntry createSearchEntry(
            String query,
            LauncherCommandTranslation launcherCommandTranslation
    ) {
        return new DefaultSearchEntry(
                "launcherCommandTranslation-" + launcherCommandTranslation.getId().toString(),
                query,
                launcherCommandTranslation.getText(),
                name()
        );
    }

    private SearchEntry createSearchEntry(
            String query,
            LauncherCommand launcherCommand
    ) {
        return new DefaultSearchEntry(
                "launcherCommand-" + launcherCommand.getId().toString(),
                query,
                launcherCommand.getName(),
                name()
        );
    }

    @Override
    public void invoke(@Nonnull SearchContext context, SearchEntry value) {
        DefaultSearchEntry searchEntry = (DefaultSearchEntry) value;

        String searchEntryId = searchEntry.getId();

        if (searchEntryId.startsWith("launcherCommand-")) {
            LauncherCommand launcherCommand = findLauncherCommandById(
                    searchEntryId.replaceAll("launcherCommand-", "")
            );
            launcherCommandExecutorAPI.launchCommand(launcherCommand);
        }
        else if (searchEntryId.startsWith("launcherCommandTranslation-")) {
            LauncherCommand launcherCommand = findLauncherCommandByTranslationId(
                    searchEntryId.replaceAll("launcherCommandTranslation-", "")
            );
            launcherCommandExecutorAPI.launchCommand(launcherCommand);
        }


    }

    private LauncherCommand findLauncherCommandById(String launcherCommandId) {
        return launcherCommandRepository.findLauncherCommandById(UUID.fromString(launcherCommandId));
    }

    private LauncherCommand findLauncherCommandByTranslationId(String launchCommandTranslationId) {
        return launcherCommandRepository.findLauncherCommandByTranslationId(UUID.fromString(launchCommandTranslationId));
    }


}
