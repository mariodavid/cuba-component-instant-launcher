package de.diedavids.cuba.instantlauncher.web.screens.validation;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.Field;
import com.haulmont.cuba.gui.components.KeyCombination;
import com.haulmont.cuba.gui.components.ValidationException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LauncherCommandShortcutValidator implements Field.Validator {

    private Logger log = LoggerFactory.getLogger(LauncherCommandShortcutValidator.class);


    public LauncherCommandShortcutValidator(Element element, String messagesPack) {
    }

    @Override
    public void validate(Object value) throws ValidationException {
        String potentialShortcut = (String)value;

        if (!hasValidShortcut(potentialShortcut)) {
            String errorMsg = String.format("Invalid Shortcut: '%s'", potentialShortcut);
            throw new ValidationException(errorMsg);
        }

    }

    private boolean hasValidShortcut(String potentialShortcut) {
        try {
            createKeyCombination(potentialShortcut);
            return true;

        } catch (IllegalArgumentException e) {
            log.warn("Shortcut {} not valid", potentialShortcut);
            return false;
        }
    }

    private KeyCombination createKeyCombination(String potentialShortcut) {
        return KeyCombination.create(potentialShortcut);
    }
}