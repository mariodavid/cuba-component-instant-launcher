package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorType;
import javax.persistence.Inheritance;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@DiscriminatorValue("LAUNCHER")
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "DDCIL_LAUNCHER_CMD")
@Entity(name = "ddcil$LauncherCommand")
public class LauncherCommand extends StandardEntity {    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    protected LauncherCommandGroup group;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @NotNull
    @Column(name = "TYPE_", nullable = false)
    protected String type;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "launcherCommand")
    protected List<LauncherCommandTranslation> translations;




    public void setGroup(LauncherCommandGroup group) {
        this.group = group;
    }

    public LauncherCommandGroup getGroup() {
        return group;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setType(LauncherCommandType type) {
        this.type = type == null ? null : type.getId();
    }

    public LauncherCommandType getType() {
        return type == null ? null : LauncherCommandType.fromId(type);
    }


    public void setTranslations(List<LauncherCommandTranslation> translations) {
        this.translations = translations;
    }

    public List<LauncherCommandTranslation> getTranslations() {
        return translations;
    }

}