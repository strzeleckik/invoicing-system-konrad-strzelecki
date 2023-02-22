package pl.futurecollars.invoicing.service

import org.junit.jupiter.api.Test
import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.db.InMemoryDatabase
import pl.futurecollars.invoicing.helpers.TestHelpers
import spock.lang.Specification


class InvoiceServiceTest extends Specification {

    private InvoiceService service
    private Database database

    def setup() {
        database = Mock(InMemoryDatabase.class)
        service = new InvoiceService(database)
    }

    def "should call database save method once while saving invoice"() {
        given:
        def invoice = TestHelpers.createInvoice()

        when:
        service.save(invoice)

        then:
        1 * database.save(invoice)
    }

    def "calling delete() should delegate to database delete() method"() {
        given:
        def invoiceId = UUID.randomUUID().toString()

        when:
        service.delete(invoiceId)

        then:
        1 * database.delete(invoiceId)
    }

    def "calling getById() should delegate to database getById() method"() {
        given:
        def invoiceId = UUID.randomUUID().toString()

        when:
        service.getById(invoiceId)

        then:
        1 * database.getById(invoiceId)
    }

    def "calling getAll() should delegate to database getAll() method"() {
        when:
        service.getAllInvoices()

        then:
        1 * database.getAll()
    }

    def "calling update() should delegate to database update() method"() {
        given:
        def invoice = TestHelpers.createInvoice()

        when:
        service.update(invoice.getId(), invoice)

        then:
        1 * database.update(invoice.getId(), invoice)
    }

    def "test"(){
        given:
        def invoice = TestHelpers.createInvoice()

        and:
        database.getById(invoice.getId()) >> Optional.of(invoice)

        when:
        def testedInvoice = service.getById(invoice.getId())

        then:
        testedInvoice.isPresent()
        testedInvoice.get().getBuyer() == invoice.getBuyer()

    }

}
