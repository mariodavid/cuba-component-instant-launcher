package de.diedavids.cuba.instantlauncher.web.launcher.executor;

import com.haulmont.cuba.core.global.BeanLocator;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Scripting;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.builders.ScreenBuilder;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.gui.screen.ScreenContext;
import com.haulmont.cuba.gui.screen.UiControllerUtils;
import com.haulmont.cuba.web.AppUI;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;
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

    @Inject
    BeanLocator beanLocator;


    @Override
    public void execute(ScriptLauncherCommand launcherCommand) {
        scripting.evaluateGroovy(launcherCommand.getLaunchScript(), createBinding());
    }

    protected Binding createBinding() {
        Binding binding = new Binding();

        ScreenContext screenContext = UiControllerUtils.getScreenContext(getFrame().getFrameOwner());

        binding.setVariable("frame", getFrame());
        binding.setVariable("notifications", screenContext.getNotifications());
        binding.setVariable("dialogs", screenContext.getDialogs());
        binding.setVariable("urlRouting", screenContext.getUrlRouting());
        binding.setVariable("screens", screenContext.getScreens());

        binding.setVariable("dataManager", dataManager);
        binding.setVariable("messages", messages);
        binding.setVariable("beanLocator", beanLocator);

        addAdditionalBindings(binding);
        return binding;
    }

    protected void addAdditionalBindings(Binding binding) {

    }

    protected Frame getFrame() {
        return getApp().getTopLevelWindow();

    }

    private AppUI getApp() {
        return AppUI.getCurrent();
    }
}
