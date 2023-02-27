package pl.futurecollars.invoicing.db

import pl.futurecollars.invoicing.helpers.TestHelper
import spock.lang.Specification

import java.time.LocalDate

abstract class AbstractDatabaseTest extends Specification {

    Database database

    abstract Database initDatabase()

    void setup() {
        database = initDatabase()
    }

    def "getAll() should return proper amount of invoices"() {
        given:
        (1..5).collect({ database.save(TestHelper.createInvoice()) })

        when:
        def invoices = database.getAll()

        then:
        invoices.size() == 5
    }

    def "getById() should return Optional.empty when invoice with given id not found"(){
        expect:
        database.getById("nonExisitngId").isEmpty()
    }

    def "getById should return optional with value"() {
        when:
        def id = database.save(TestHelper.createInvoice())

        then:
        database.getById(id).isPresent()
    }

    def "should update invoice properly"(){
        given: "saving new invoice in db"
        def invoice = TestHelper.createInvoice();
        def id = database.save(invoice)

        and: "i edit invoices fields"
        invoice.setId(id)
        invoice.setBuyer("edited buyer")
        invoice.setDate(LocalDate.of(2020, 12, 12))
        invoice.setSeller("edited seller")
        invoice.setEntries(
                (1..4).collect(
                        {TestHelper.createInvoiceEntry()}
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
        def id = database.save(TestHelper.createInvoice())

        when:
        database.delete(id)

        then:
        database.getById(id).isEmpty()
    }

}
