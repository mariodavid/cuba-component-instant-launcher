package de.diedavids.cuba.instantlauncher.web.launcher;

import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;

import java.util.Map;

public interface LauncherCommandExecutor<T extends LauncherCommand> {

    void execute(T launcherCommand, Map<String, Object> inputParams);
    void execute(T launcherCommand);
}
