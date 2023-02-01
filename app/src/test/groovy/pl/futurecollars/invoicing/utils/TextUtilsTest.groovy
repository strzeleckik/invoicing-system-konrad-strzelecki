package pl.futurecollars.invoicing.utils

import spock.lang.Specification

class TextUtilsTest extends Specification {
    def "CapitalizeText"() {
        given:
            def text = "testText"

        when:
            def result = TextUtils.capitalizeText(text)

        then:
            result == "TESTTEXT"
    }
}
