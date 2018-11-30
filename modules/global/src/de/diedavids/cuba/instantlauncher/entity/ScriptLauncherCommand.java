package de.diedavids.cuba.instantlauncher.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("SCRIPT_LAUNCHER")
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
@Table(name = "DDCIL_SCRIPT_LAUNCHER_CMD")
@Entity(name = "ddcil$ScriptLauncherCommand")
public class ScriptLauncherCommand extends LauncherCommand {
    private static final long serialVersionUID = 6146649236362231720L;

    @NotNull
    @Lob
    @Column(name = "LAUNCH_SCRIPT", nullable = false)
    protected String launchScript;

    public void setLaunchScript(String launchScript) {
        this.launchScript = launchScript;
    }

    public String getLaunchScript() {
        return launchScript;
    }


}