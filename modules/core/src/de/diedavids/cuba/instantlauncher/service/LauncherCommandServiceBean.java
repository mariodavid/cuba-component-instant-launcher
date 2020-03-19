package de.diedavids.cuba.instantlauncher.service;

import com.haulmont.cuba.core.global.DataManager;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandGroup;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandTranslation;
import de.diedavids.cuba.instantlauncher.repository.LauncherCommandRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service(LauncherCommandService.NAME)
public class LauncherCommandServiceBean implements LauncherCommandService {

    @Inject
    protected LauncherCommandRepository launcherCommandRepository;
    @Inject
    protected DataManager dataManager;

    @Override
    public List<LauncherCommandTranslation> findAllLauncherCommandTranslationByTextAndLocale(String query, Locale locale) {
        return launcherCommandRepository.findAllLauncherCommandTranslationByTextAndLocale(query, locale);
    }

    @Override
    public LauncherCommand loadWithDetails(LauncherCommand launcherCommand) {
        return dataManager.reload(launcherCommand, "launcherCommand-with-details");
    }

    @Override
    public List<LauncherCommand> findAllLauncherCommandsByName(String query) {
        return launcherCommandRepository.findAllLauncherCommandsByName(query);
    }

    @Override
    public LauncherCommand findLauncherCommandByTranslationId(UUID id) {
        return launcherCommandRepository.findLauncherCommandByTranslationId(id);
    }

    @Override
    public List<LauncherCommand> findAllLauncherCommandsWithShortcuts() {
        return launcherCommandRepository.findAllLauncherCommandsWithShortcuts();
    }

    @Override
    public List<LauncherCommand> findAllMainMenuLauncherCommands(String view) {
        return launcherCommandRepository.findAllMainMenuLauncherCommands(view);
    }

    @Override
    public LauncherCommand findLauncherCommandById(UUID launcherCommandId) {
        return launcherCommandRepository.findLauncherCommandById(launcherCommandId);
    }

    @Override
    public List<LauncherCommandGroup> findAllMainMenuGroups(String view) {
        return launcherCommandRepository.findAllMainMenuGroups(view);
    }
}