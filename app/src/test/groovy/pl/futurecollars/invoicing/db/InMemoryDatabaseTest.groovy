package pl.futurecollars.invoicing.db

class InMemoryDatabaseTest extends AbstractDatabaseTest {
    @Override
    Database getDatabaseInstance() {
        return new InMemoryDatabase()
    }
}
