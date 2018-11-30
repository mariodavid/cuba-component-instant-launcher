package de.diedavids.cuba.instantlauncher;

import com.google.common.base.Strings;
import com.haulmont.chile.core.datatypes.Datatype;
import java.text.ParseException;
import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LocaleDatatype implements Datatype<Locale> {

    @Override
    public Class getJavaClass() {
        return Locale.class;
    }

    @Nonnull
    @Override
    public String format(@Nullable Object value) {

        Locale locale = (Locale) value;

        if (locale == null) {
            return "";
        }
        return locale.toLanguageTag();
    }


    @Nonnull
    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public Locale parse(@Nullable String value) throws ParseException {

        if (Strings.isNullOrEmpty(value))
            return null;

        return Locale.forLanguageTag(value);
    }

    @Nullable
    @Override
    public Locale parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }

}
