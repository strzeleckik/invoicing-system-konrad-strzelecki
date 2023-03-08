package pl.futurecollars.invoicing.helpers

import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry

import java.time.LocalDate

class TestHelper {

    static Invoice createInvoice(String id){
        Invoice invoice = createInvoice()
        invoice.setId(id)
        invoice.setBuyer(company(id + "buyer"))
        invoice.setSeller(company(id))
        return invoice
    }

    static Invoice createInvoice(){
        def id = UUID.randomUUID().toString()
        return Invoice.builder()
                .id(id)
                .date(LocalDate.now())
                .buyer(company(id+"buyer"))
                .seller(company(id))
                .entries(List.of(InvoiceEntry
                        .builder()
                        .quantity(1)
                        .price(BigDecimal.valueOf(1.3))
                        .build()))
                .build()
    }

    static InvoiceEntry createInvoiceEntry() {
        return InvoiceEntry
                .builder()
                .quantity(3)
                .price(BigDecimal.valueOf(100.2))
                .build()
    }

    static company(String taxId) {
        Company.builder()
                .taxIdentificationNumber("$taxId")
                .address("ul. testowa 24d/$taxId 02-703 Warszawa, Polska")
                .name("Test company $taxId Sp. z o.o")
                .build()
    }
}
