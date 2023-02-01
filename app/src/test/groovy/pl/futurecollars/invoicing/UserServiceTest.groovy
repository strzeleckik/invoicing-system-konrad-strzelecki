package pl.futurecollars.invoicing

import pl.futurecollars.invoicing.model.User
import spock.lang.Specification

class UserServiceTest extends Specification {

    def "CapitalizeUserName"() {
        given:
        def user = User.builder().name("Konrad").build()
        def userService = new UserService();
        when:
        def result = userService.capitalizeUserName(user.getName())

        then:
        result == "KONRAD"
    }
}
