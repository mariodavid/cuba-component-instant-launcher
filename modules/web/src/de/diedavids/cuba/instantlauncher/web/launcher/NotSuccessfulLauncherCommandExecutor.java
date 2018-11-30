package de.diedavids.cuba.instantlauncher.web.launcher;

import com.haulmont.cuba.web.AppUI;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import org.springframework.stereotype.Component;

@Component("ddcil_NotSuccessfulLauncher")
public class NotSuccessfulLauncherCommandExecutor implements LauncherCommandExecutor {

    @Override
    public void execute(LauncherCommand launcherCommand) {
        AppUI.getCurrent().getTopLevelWindow().showNotification("Not successful");
    }

}
