package de.diedavids.cuba.instantlauncher;

import java.util.Locale;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocaleToLanguageTagConverter implements AttributeConverter<Locale, String> {

    /**
     * Creates a new {@link LocaleToLanguageTagConverter}.
     */
    public LocaleToLanguageTagConverter() {
        super();
    }

    /**
     * Calls the {@link Locale#toLanguageTag()} method on the supplied
     * {@link Locale} and returns its result.
     *
     * <p>This method may return {@code null}.</p>
     *
     * @param locale the {@link Locale} to convert; if {@code null},
     * then {@code null} will be returned
     *
     * @return the {@linkplain Locale#toLanguageTag() language tag
     * representation} of the supplied {@link Locale}, or {@code null}
     *
     * @see Locale#toLanguageTag()
     */
    @Override
    public String convertToDatabaseColumn(final Locale locale) {
        if (locale == null) {
            return null;
        }
        return locale.toLanguageTag();
    }

    /**
     * Passes the supplied {@code languageTag} to the {@link
     * Locale#forLanguageTag(String)} method and returns its result.
     *
     * <p>This method may return {@code null}.</p>
     *
     * @param languageTag a <a
     * href="http://tools.ietf.org/html/bcp47">BCP 47</a>-compliant
     * language tag suitable for passing to the {@link
     * Locale#forLanguageTag(String)} method; may be {@code null} in
     * which case {@code null} will be returned
     *
     * @return a {@link Locale} corresponding to the supplied {@code
     * languageTag}, or {@code null}
     */
    @Override
    public Locale convertToEntityAttribute(final String languageTag) {
        if (languageTag == null) {
            return null;
        }
        return Locale.forLanguageTag(languageTag);
    }

}
