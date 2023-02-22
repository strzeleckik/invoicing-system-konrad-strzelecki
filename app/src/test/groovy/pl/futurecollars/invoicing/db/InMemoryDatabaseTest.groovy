package pl.futurecollars.invoicing.db

import pl.futurecollars.invoicing.helpers.TestHelper

class InMemoryDatabaseTest extends AbstractDatabaseTest {
    @Override
    Database initDatabase() {
        return new InMemoryDatabase()
    }

    def "should create invoice with id even if no id declared"(){
        given:
        def invoice = TestHelper.createInvoice();
        invoice.setId(null)

        when:
        def newInvoiceId = database.save(invoice)

        then:
        newInvoiceId != null
    }
}
