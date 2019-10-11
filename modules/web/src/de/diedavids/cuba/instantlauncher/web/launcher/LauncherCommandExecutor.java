package de.diedavids.cuba.instantlauncher.web.launcher;

import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;

import java.util.Map;

public interface LauncherCommandExecutor<T extends LauncherCommand> {


    /**
     * executes a given launcher command
     * @param launcherCommand the launcher command to execute
     */
    void execute(T launcherCommand);

    /**
     * executes a given launcher command
     * @param launcherCommand the launcher command to execute
     * @param inputParams the input parameter provided by the user
     */
    void execute(T launcherCommand, Map<String, Object> inputParams);

}
