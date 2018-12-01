package de.diedavids.cuba.instantlauncher.entity;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
@Table(name = "DDCIL_BEAN_LAUNCHER_COMMAND")
@Entity(name = "ddcil$BeanLauncherCommand")
public class BeanLauncherCommand extends LauncherCommand {
    private static final long serialVersionUID = -6912123191982100627L;

    @NotNull
    @Column(name = "BEAN_NAME", nullable = false)
    protected String beanName;

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    @PostConstruct
    protected void initType() {
        setType(LauncherCommandType.BEAN_LAUNCHER);
    }


}