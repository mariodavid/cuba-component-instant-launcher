package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@Table(name = "DDCIL_LAUNCHER_COMMAND_GROUP_TRANSLATION")
@Entity(name = "ddcil_LauncherCommandGroupTranslation")
public class LauncherCommandGroupTranslation extends StandardEntity {
    private static final long serialVersionUID = -2784295470208017659L;

    @MetaProperty(datatype = "Locale", mandatory = true)
    @NotNull
    @Column(name = "LOCALE", nullable = false)
    protected Locale locale;

    @NotNull
    @Column(name = "TEXT", nullable = false)
    protected String text;

    @Lookup(type = LookupType.DROPDOWN)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LAUNCHER_COMMAND_GROUP_ID")
    protected LauncherCommandGroup launcherCommandGroup;

    public LauncherCommandGroup getLauncherCommandGroup() {
        return launcherCommandGroup;
    }

    public void setLauncherCommandGroup(LauncherCommandGroup launcherCommandGroup) {
        this.launcherCommandGroup = launcherCommandGroup;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}