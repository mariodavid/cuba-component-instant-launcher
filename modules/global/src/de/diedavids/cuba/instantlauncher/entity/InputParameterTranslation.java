package de.diedavids.cuba.instantlauncher.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@Table(name = "DDCIL_INPUT_PARAMETER_TRANSLATION")
@Entity(name = "ddcil_InputParameterTranslation")
public class InputParameterTranslation extends StandardEntity {
    private static final long serialVersionUID = 7581912816799254436L;

    @NotNull
    @Column(name = "TEST", nullable = false)
    protected String text;

    @NotNull
    @MetaProperty(datatype = "Locale", mandatory = true)
    @Column(name = "LOCALE", nullable = false)
    protected Locale locale;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INPUT_PARAMETER_ID")
    protected InputParameter inputParameter;

    public InputParameter getInputParameter() {
        return inputParameter;
    }

    public void setInputParameter(InputParameter inputParameter) {
        this.inputParameter = inputParameter;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}