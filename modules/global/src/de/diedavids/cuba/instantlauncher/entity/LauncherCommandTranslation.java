package de.diedavids.cuba.instantlauncher.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Index;
import com.haulmont.chile.core.annotations.MetaProperty;
import de.diedavids.cuba.instantlauncher.LocaleDatatype;
import java.util.Locale;

@NamePattern("%s|text")
@Table(name = "DDCIL_LAUNCHER_CMD_TRANSLATION", indexes = {
    @Index(name = "IDX_DDCIL_LAUNCHER_COMMAND_TRANSLATION", columnList = "LOCALE, TEXT")
})
@Entity(name = "ddcil$LauncherCommandTranslation")
public class LauncherCommandTranslation extends StandardEntity {
    private static final long serialVersionUID = 2332617624716687431L;

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
    @JoinColumn(name = "LAUNCHER_COMMAND_ID")
    protected LauncherCommand launcherCommand;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }



    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setLauncherCommand(LauncherCommand launcherCommand) {
        this.launcherCommand = launcherCommand;
    }

    public LauncherCommand getLauncherCommand() {
        return launcherCommand;
    }


}