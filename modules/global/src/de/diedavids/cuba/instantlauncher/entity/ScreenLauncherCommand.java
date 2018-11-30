package de.diedavids.cuba.instantlauncher.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import com.haulmont.cuba.core.global.DesignSupport;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("SCREEN_LAUNCHER")
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
@Table(name = "DDCIL_SCREEN_LAUNCHER_CMD")
@Entity(name = "ddcil$ScreenLauncherCommand")
public class ScreenLauncherCommand extends LauncherCommand {
    private static final long serialVersionUID = -9023554095308305336L;

    @NotNull
    @Column(name = "SCREEN_ID", nullable = false)
    protected String screenId;

    @NotNull
    @Column(name = "SCREEN_LAUNCHER_COMMAND_TYPE", nullable = false)
    protected String screenLauncherCommandType;

    @Lob
    @Column(name = "SCREEN_PARAMETERS_SCRIPT")
    protected String screenParametersScript;

    @Column(name = "OPEN_TYPE")
    protected String openType;

    @Column(name = "SCREEN_ENTITY")
    protected String screenEntity;

    public void setScreenLauncherCommandType(ScreenLauncherCommandType screenLauncherCommandType) {
        this.screenLauncherCommandType = screenLauncherCommandType == null ? null : screenLauncherCommandType.getId();
    }

    public ScreenLauncherCommandType getScreenLauncherCommandType() {
        return screenLauncherCommandType == null ? null : ScreenLauncherCommandType.fromId(screenLauncherCommandType);
    }


    public void setScreenEntity(String screenEntity) {
        this.screenEntity = screenEntity;
    }

    public String getScreenEntity() {
        return screenEntity;
    }


    public void setOpenType(ScreenLauncherOpenType openType) {
        this.openType = openType == null ? null : openType.getId();
    }

    public ScreenLauncherOpenType getOpenType() {
        return openType == null ? null : ScreenLauncherOpenType.fromId(openType);
    }


    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenParametersScript(String screenParametersScript) {
        this.screenParametersScript = screenParametersScript;
    }

    public String getScreenParametersScript() {
        return screenParametersScript;
    }


}