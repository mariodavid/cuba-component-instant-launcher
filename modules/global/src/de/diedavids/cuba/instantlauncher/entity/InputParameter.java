package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "DDCIL_USER_INPUT_PARAMETER")
@Entity(name = "ddcil_UserInputParameter")
public class InputParameter extends StandardEntity {
    private static final long serialVersionUID = 3248734403893694827L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "REQUIRED")
    protected Boolean required;

    @NotNull
    @Column(name = "TYPE_", nullable = false)
    protected String type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LAUNCHER_COMMAND_ID")
    protected LauncherCommand launcherCommand;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "inputParameter")
    protected List<InputParameterTranslation> translations;

    @Column(name = "ENUMERATION_CLASS")
    protected String enumerationClass;

    @MetaProperty(datatype = "MetaClass")
    @Column(name = "ENTITY_CLASS")
    protected MetaClass entityClass;

    public MetaClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(MetaClass entityClass) {
        this.entityClass = entityClass;
    }

    public String getEnumerationClass() {
        return enumerationClass;
    }

    public void setEnumerationClass(String enumerationClass) {
        this.enumerationClass = enumerationClass;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public List<InputParameterTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<InputParameterTranslation> translations) {
        this.translations = translations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LauncherCommand getLauncherCommand() {
        return launcherCommand;
    }

    public void setLauncherCommand(LauncherCommand launcherCommand) {
        this.launcherCommand = launcherCommand;
    }

    public InputParameterType getType() {
        return type == null ? null : InputParameterType.fromId(type);
    }

    public void setType(InputParameterType type) {
        this.type = type == null ? null : type.getId();
    }
}