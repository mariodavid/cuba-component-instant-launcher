package de.diedavids.cuba.instantlauncher.web.launcher


import de.diedavids.cuba.instantlauncher.entity.InputParameter
import de.diedavids.cuba.instantlauncher.entity.InputParameterType
import spock.lang.Specification
import spock.lang.Unroll

class LauncherCommandInputParameterFactorySpec extends Specification {
    LauncherCommandInputParameterFactory sut

    def setup() {
        sut = new LauncherCommandInputParameterFactory()
    }

    @Unroll
    def "create creates a #resultType Input Parameter for the type #type"() {
        given:
        def persistedInputParameter = new InputParameter(
                name: "myParam",
                type: type
        )
        when:
        def result = sut.create(persistedInputParameter)

        then:
        result.getDatatypeJavaClass() == resultType

        where:
        type                        || resultType
        InputParameterType.STRING   || String
        InputParameterType.INTEGER  || Integer
        InputParameterType.BOOLEAN  || Boolean
        InputParameterType.DATE     || java.sql.Date
        InputParameterType.DATETIME || java.util.Date
    }


    def "create sets the required attribute of the input parameter"() {
        given:
        def persistedInputParameter = new InputParameter(
                name: "myParam",
                type: InputParameterType.STRING,
                required: true
        )

        when:
        def result = sut.create(persistedInputParameter)

        then:
        result.isRequired()
    }

}
