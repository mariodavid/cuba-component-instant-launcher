package de.diedavids.cuba.instantlauncher.web.launcher.executor

import com.haulmont.cuba.core.global.Scripting
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor;

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
