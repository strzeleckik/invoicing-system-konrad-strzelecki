package pl.futurecollars.invoicing.service


import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.helpers.TestHelper
import spock.lang.Specification

class InvoiceServiceTest extends Specification {

    private InvoiceService invoiceService

    private Database database

    def setup() {
        database = Mock()
        invoiceService = new InvoiceService(database)
    }

    def "should call database save method once while saving invoice"() {
        given:
        def invoice = TestHelper.createInvoice()

        when:
        invoiceService.save(invoice)

        then:
        1 * database.save(invoice)
    }

    def "calling delete() should delegate to database delete() method"() {
        given:
        def invoiceId = UUID.randomUUID().toString()
        when:
        invoiceService.delete(invoiceId)
        then:
        1 * database.delete(invoiceId)
    }

    def "calling getById() should delegate to database getById() method"() {
        given:
        def invoiceId = UUID.randomUUID().toString()
        when:
        invoiceService.getById(invoiceId)
        then:
        1 * database.getById(invoiceId)
    }

    def "calling getAll() should delegate to database getAll() method"() {
        when:
        invoiceService.getAllInvoices()
        then:
        1 * database.getAll()
    }

    def "calling update() should delegate to database update() method"() {
        given:
        def invoice = TestHelper.createInvoice()
        when:
        invoiceService.update(invoice.getId(), invoice)
        then:
        1 * database.update(invoice.getId(), invoice)
    }

    def "calling getById should return expected invoice"() {
        given:
        def invoice = TestHelper.createInvoice()

        and:
        database.getById(invoice.getId()) >> Optional.of(invoice)

        when:
        def resultInvoice = invoiceService.getById(invoice.getId())

        then:
        resultInvoice.isPresent()
        resultInvoice.get() == invoice


    }


}
