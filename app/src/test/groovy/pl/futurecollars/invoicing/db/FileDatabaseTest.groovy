package pl.futurecollars.invoicing.db

import com.fasterxml.jackson.databind.ObjectMapper
import pl.futurecollars.invoicing.configuration.AppConfiguration
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.service.FileService
import spock.lang.Specification

import java.time.LocalDate

class FileDatabaseTest extends Specification {

    private Database database

    def "get all should return empty list when file with db data not exists"() {
        given:
            def appConfig = new AppConfiguration("notExistingFile.json")
            database = new FileDatabase(new FileService(appConfig.objectMapper()), appConfig);

        when:
            def invoices = database.getAll()

        then:
            0 == invoices.size()

    }

    def "get all should return size 2 list when file with db data exists and records added"() {
        given:
        def tmpFilePath = File.createTempFile('test', '.txt').getAbsolutePath()
        def appConfig = new AppConfiguration(tmpFilePath)
        database = new FileDatabase(new FileService(appConfig.objectMapper()), appConfig)

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
