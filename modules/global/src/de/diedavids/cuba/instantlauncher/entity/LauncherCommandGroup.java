package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;

@NamePattern("%s|name")
@Table(name = "DDCIL_LAUNCHER_COMMAND_GROUP")
@Entity(name = "ddcil$LauncherCommandGroup")
public class LauncherCommandGroup extends StandardEntity {
    private static final long serialVersionUID = -589824613119680108L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "CODE")
    protected String code;

    @Column(name = "SHOW_IN_MAIN_MENU")
    protected Boolean showInMainMenu;

    @OneToMany(mappedBy = "launcherCommandGroup")
    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    protected List<LauncherCommandGroupTranslation> translations;

    public void setTranslations(List<LauncherCommandGroupTranslation> translations) {
        this.translations = translations;
    }

    public List<LauncherCommandGroupTranslation> getTranslations() {
        return translations;
    }

    public Boolean getShowInMainMenu() {
        return showInMainMenu;
    }

    public void setShowInMainMenu(Boolean showInMainMenu) {
        this.showInMainMenu = showInMainMenu;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    public String translationForLocale(Locale locale) {
        return getTranslations()
                .stream()
                .filter(translation -> locale.equals(translation.getLocale()))
                .map(LauncherCommandGroupTranslation::getText)
                .findFirst()
                .orElse(getName());

    }

}