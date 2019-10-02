package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.cuba.core.global.MessageTools
import com.haulmont.cuba.core.global.UserSessionSource
import de.diedavids.cuba.instantlauncher.entity.InputParameter
import de.diedavids.cuba.instantlauncher.entity.InputParameterTranslation
import de.diedavids.cuba.instantlauncher.entity.InputParameterType
import spock.lang.Specification
import spock.lang.Unroll

class LauncherCommandInputParameterFactoryTranslationSpec extends Specification {
    LauncherCommandInputParameterFactory sut
    UserSessionSource userSessionSource
    MessageTools messageTools

    def setup() {
        userSessionSource = Mock(UserSessionSource)
        userSessionSource.checkCurrentUserSession() >> true

        messageTools = Mock(MessageTools)
        sut = new LauncherCommandInputParameterFactory(
                userSessionSource: userSessionSource,
                messageTools: messageTools
        )
    }

    def "create sets the caption according to the current user locale"() {
        given:
        def persistedInputParameter = new InputParameter(
                name: "myParam",
                type: InputParameterType.STRING,
                translations: [
                        translation(Locale.ENGLISH, "My Parameter"),
                        translation(Locale.GERMAN, "Mein Parameter")
                ]
        )

        and:
        userLocaleIsSetTo(Locale.GERMAN)

        when:
        def result = sut.create(persistedInputParameter)

        then:
        result.getCaption() == "Mein Parameter"
    }

    def "create takes the name as the caption if no translation is available"() {
        given:
        def persistedInputParameter = new InputParameter(
                name: "myParam",
                type: InputParameterType.STRING,
                translations: []
        )

        and:
        userLocaleIsSetTo(Locale.GERMAN)

        when:
        def result = sut.create(persistedInputParameter)

        then:
        result.getCaption() == "myParam"
    }

    def "create takes the default locale as the caption if no translation for the current user locale is available"() {
        given:
        def persistedInputParameter = new InputParameter(
                name: "myParam",
                type: InputParameterType.STRING,
                translations: [
                        translation(Locale.ENGLISH, "My Parameter"),
                        translation(Locale.GERMAN, "Mein Parameter")
                ]
        )

        and:
        systemDetaultLocaleIsSetTo(Locale.ENGLISH)

        userLocaleIsSetTo(Locale.FRENCH)

        when:
        def result = sut.create(persistedInputParameter)

        then:
        result.getCaption() == "My Parameter"
    }

    private InputParameterTranslation translation(Locale locale, String value) {
        new InputParameterTranslation(
                locale: locale,
                text: value
        )
    }

    void systemDetaultLocaleIsSetTo(Locale locale) {
        messageTools.getDefaultLocale() >> locale
    }

    void userLocaleIsSetTo(Locale locale) {
        userSessionSource.getLocale() >> locale
    }
}
