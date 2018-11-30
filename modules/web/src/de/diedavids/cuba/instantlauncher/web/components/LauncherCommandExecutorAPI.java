package de.diedavids.cuba.instantlauncher.web.components;

import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;

public interface LauncherCommandExecutorAPI {

    static String NAME = "ddcil_LauncherCommandExecutor";

    void launchCommand(LauncherCommand launcherCommand);

}
