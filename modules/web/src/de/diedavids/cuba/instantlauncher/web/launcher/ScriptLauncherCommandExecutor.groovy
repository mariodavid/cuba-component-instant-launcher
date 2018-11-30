package de.diedavids.cuba.instantlauncher.web.launcher;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Scripting;
import com.haulmont.cuba.web.AppUI;
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand;
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component("ddcil_ScriptLauncherCommandExecutor")
class ScriptLauncherCommandExecutor implements LauncherCommandExecutor<ScriptLauncherCommand> {


    @Inject
    Scripting scripting;

    @Override
    void execute(ScriptLauncherCommand launcherCommand) {
        scripting.evaluateGroovy(launcherCommand.getLaunchScript(), createBinding())
    }

    protected Binding createBinding() {
        new Binding()
    }

}
