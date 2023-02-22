package pl.futurecollars.invoicing

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.futurecollars.invoicing.service.InvoiceService
import spock.lang.Specification

@SpringBootTest
class InvoicingAppTest extends Specification {

    @Autowired
    InvoiceService invoiceService

    def "expect invoice service to be initialized"() {
        expect:
        invoiceService != null
    }
}
