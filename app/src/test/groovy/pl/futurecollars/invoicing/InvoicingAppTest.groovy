package pl.futurecollars.invoicing


import pl.futurecollars.invoicing.service.InvoiceService
import spock.lang.Specification

class InvoicingAppTest extends Specification {


    private InvoiceService invoiceService;

    def "application main starts without problems"() {
        setup:
        def app = new InvoicingApp()

        and:
        app.main()
    }
}