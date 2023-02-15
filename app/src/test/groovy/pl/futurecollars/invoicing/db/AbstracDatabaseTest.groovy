package pl.futurecollars.invoicing.db

import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import spock.lang.Specification

import java.time.LocalDate

abstract class AbstractDatabaseTest extends Specification {

    private Database database;

    abstract Database getDatabaseInstance()

    void setup() {
        database = getDatabaseInstance()
    }

    Invoice createInvoice() {
        return Invoice.builder()
                .date(LocalDate.of(2022, 01, 01))
                .buyer("test Buyer")
                .seller("test Seller")
                .entries(List.of(InvoiceEntry
                        .builder()
                        .quantity(1)
                        .price(BigDecimal.valueOf(1.3))
                        .build()))
                .build()
    }

    def "get all should return proper amount of invoice items when records added properly"() {
        when:
        database.save(createInvoice())
        database.save(createInvoice())
        def invoices = database.getAll()

        then:
        2 == invoices.size()
    }


    def "getById Should return optional.empty when invoice not found"() {
        expect:
        database.getById("notExistingId").isEmpty()
    }

    def "getById should return optional with value"() {
        when:
        def id = database.save(createInvoice())

        then:
        database.getById(id).isPresent()
    }

    def "should update invoice properly"(){
        given: "saving new invoice in db"
        def invoice = createInvoice();
        def id = database.save(invoice)

        and: "i edit invoices fields"
        invoice.setId(id)
        invoice.setBuyer("edited buyer")
        invoice.setDate(LocalDate.of(2020, 12, 12))
        invoice.setSeller("edited seller")
        invoice.setEntries(List.of(InvoiceEntry
                .builder()
                .quantity(3)
                .price(BigDecimal.valueOf(100.2))
                .build()))

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
        def id = database.save(createInvoice())

        when:
        database.delete(id)

        then:
        database.getById(id).isEmpty()
    }

}
