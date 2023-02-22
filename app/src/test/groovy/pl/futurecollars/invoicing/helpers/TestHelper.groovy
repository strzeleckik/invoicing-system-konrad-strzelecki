package pl.futurecollars.invoicing.helpers

import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry

import java.time.LocalDate

class TestHelper {

    static Invoice createInvoice(String id){
        Invoice invoice = createInvoice()
        invoice.setId(id)
        return invoice
    }

    static Invoice createInvoice(){
        return Invoice.builder()
                .id(UUID.randomUUID().toString())
                .date(LocalDate.now())
                .buyer("test buyer")
                .seller("testSeller")
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
}
