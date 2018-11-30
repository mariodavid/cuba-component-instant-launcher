package de.diedavids.cuba.instantlauncher.web.launcher;

import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;

public interface LauncherCommandExecutor<T extends LauncherCommand> {

    void execute(T launcherCommand);
}
