package pl.futurecollars.invoicing.db

import pl.futurecollars.invoicing.helpers.TestHelpers
import spock.lang.Specification

import java.time.LocalDate

abstract class AbstractDatabaseTest extends Specification {

    Database database;

    abstract Database getDatabaseInstance()

    void setup() {
        database = getDatabaseInstance()
    }

    def "get all should return proper amount of invoice items when records added properly"() {
        when:
        (1..5).collect({database.save(TestHelpers.createInvoice())})
        def invoices = database.getAll()

        then:
        5 == invoices.size()
    }

    def "getById Should return optional.empty when invoice not found"() {
        expect:
        database.getById("notExistingId").isEmpty()
    }

    def "getById should return optional with value"() {
        when:
        def id = database.save(TestHelpers.createInvoice())

        then:
        database.getById(id).isPresent()
    }

    def "should update invoice properly"(){
        given: "saving new invoice in db"
        def invoice = TestHelpers.createInvoice();
        def id = database.save(invoice)

        and: "i edit invoices fields"
        invoice.setId(id)
        invoice.setBuyer("edited buyer")
        invoice.setDate(LocalDate.of(2020, 12, 12))
        invoice.setSeller("edited seller")
        invoice.setEntries(
                (1..4).collect(
                        {TestHelpers.createInvoiceEntry()}
                ))

        when: "i call update method"

        database.update(id, invoice)
        def editedInvoice = database.getById(id)

        then: "i should get updated result"
        editedInvoice.isPresent()
        editedInvoice.get().id == invoice.id
        editedInvoice.get().buyer == invoice.buyer
        editedInvoice.get().seller == invoice.seller
        editedInvoice.get().entries == invoice.entries
    }

    def "should delete element"(){
        given:
        def id = database.save(TestHelpers.createInvoice())

        when:
        database.delete(id)

        then:
        database.getById(id).isEmpty()
    }
}
