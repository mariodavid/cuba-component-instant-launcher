package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.chile.core.model.MetaClass
import com.haulmont.cuba.security.entity.Role
import com.haulmont.cuba.security.entity.RoleType
import de.diedavids.cuba.instantlauncher.entity.InputParameter
import de.diedavids.cuba.instantlauncher.entity.InputParameterType
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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
        type                               || resultType
        InputParameterType.STRING          || String
        InputParameterType.INTEGER         || Integer
        InputParameterType.BOOLEAN         || Boolean
        InputParameterType.DATE            || java.sql.Date
        InputParameterType.DATETIME        || java.util.Date
        InputParameterType.LOCAL_DATE      || LocalDate
        InputParameterType.LOCAL_DATE_TIME || LocalDateTime
        InputParameterType.LOCAL_TIME      || LocalTime
        InputParameterType.DOUBLE          || Double
        InputParameterType.BIG_DECIMAL     || BigDecimal
    }

    def "create with an enum returns a enum input parameter"() {
        given:
        def persistedInputParameter = new InputParameter(
                name: "myParam",
                type: InputParameterType.ENUMERATION,
                enumerationClass: RoleType.class.name
        )
        when:
        def result = sut.create(persistedInputParameter)

        then:
        result.enumClass == RoleType

    }

    def "create with an entity returns a entity input parameter"() {
        def roleMetaClass = Mock(MetaClass)
        roleMetaClass.getName() >> 'sec$Role'
        roleMetaClass.getJavaClass() >> Role

        given:
        def persistedInputParameter = new InputParameter(
                name: "myParam",
                type: InputParameterType.ENTITY,
                entityClass: roleMetaClass
        )
        when:
        def result = sut.create(persistedInputParameter)

        then:
        result.entityClass == Role

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
