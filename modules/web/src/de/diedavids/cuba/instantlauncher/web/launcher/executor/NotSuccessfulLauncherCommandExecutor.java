package de.diedavids.cuba.instantlauncher.web.launcher.executor;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.web.AppUI;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("ddcil_NotSuccessfulLauncherCommandExecutor")
public class NotSuccessfulLauncherCommandExecutor implements LauncherCommandExecutor {

    @Override
    public void execute(LauncherCommand launcherCommand, Map inputParams) {
        AppUI.getCurrent().getTopLevelWindow().showNotification("Not successful");
    }

    @Override
    public void execute(LauncherCommand launcherCommand) {
        execute(launcherCommand, ParamsMap.empty());
    }

}
