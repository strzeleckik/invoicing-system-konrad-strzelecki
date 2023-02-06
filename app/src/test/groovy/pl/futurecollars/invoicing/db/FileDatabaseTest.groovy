package pl.futurecollars.invoicing.db

import pl.futurecollars.invoicing.configuration.Configuration
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.service.FileService
import spock.lang.Specification

import java.time.LocalDate

class FileDatabaseTest extends Specification {

    private Database database

    def "get all should return empty list when file with db data not exists"() {
        given:
            database = new FileDatabase(new FileService(), new Configuration("notExistingFile.json"));

        when:
            def invoices = database.getAll()

        then:
            0 == invoices.size()

    }

    def "get all should return size 2 list when file with db data exists and records added"() {
        given:
        def tmpFilePath = File.createTempFile('test', '.txt').getAbsolutePath()
        database = new FileDatabase(new FileService(), new Configuration(tmpFilePath))

        when:

        database.save(Invoice.builder()
                .date(LocalDate.now())
                .buyer("test buyer")
                .seller("testSeller")
                .entries(List.of(InvoiceEntry
                        .builder()
                        .quantity(1)
                        .price(BigDecimal.valueOf(1.3))
                        .build()))
                .build())

        database.save(Invoice.builder()
                .date(LocalDate.now())
                .buyer("test buyer")
                .seller("testSeller")
                .build())

        def invoices = database.getAll()

        then:
        2 == invoices.size()

    }
}
