package de.diedavids.cuba.instantlauncher.web.launcher.executor;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Scripting;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.web.AppUI;
import de.diedavids.cuba.instantlauncher.entity.ScriptLauncherCommand;
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandExecutor;

import groovy.lang.Binding;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component("ddcil_ScriptLauncherCommandExecutor")
public class ScriptLauncherCommandExecutor implements LauncherCommandExecutor<ScriptLauncherCommand> {

    @Inject
    Scripting scripting;

    @Inject
    DataManager dataManager;

    @Inject
    Messages messages;


    @Override
    public void execute(ScriptLauncherCommand launcherCommand) {
        scripting.evaluateGroovy(launcherCommand.getLaunchScript(), createBinding());
    }

    protected Binding createBinding() {
        Binding binding = new Binding();

        binding.setVariable("frame", getFrame());
        binding.setVariable("dataManager", dataManager);
        binding.setVariable("messages", messages);

        addAdditionalBindings(binding);
        return binding;
    }

    protected void addAdditionalBindings(Binding binding) {

    }

    protected Frame getFrame() {
        return AppUI.getCurrent().getTopLevelWindow();
    }
}
