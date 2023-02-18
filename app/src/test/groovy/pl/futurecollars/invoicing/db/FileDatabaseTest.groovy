package pl.futurecollars.invoicing.db

import pl.futurecollars.invoicing.configuration.InvoicingAppConfiguration
import pl.futurecollars.invoicing.service.FileService


class FileDatabaseTest extends AbstractDatabaseTest {

    @Override
    Database getDatabaseInstance() {
        def tmpFilePath = File.createTempFile('test', '.txt').getAbsolutePath()
        def appConfig = new InvoicingAppConfiguration(tmpFilePath)
        return new FileDatabase(new FileService(appConfig.objectMapper()), appConfig)
    }

    def "get all should return empty list when file with db data not exists"() {
        given:
        def appConfig = new InvoicingAppConfiguration("notExistingFile.json")
        def notExistingDb = new FileDatabase(new FileService(appConfig.objectMapper()), appConfig)

        when:
        def invoices = notExistingDb.getAll()

        then:
        0 == invoices.size()

    }


}
