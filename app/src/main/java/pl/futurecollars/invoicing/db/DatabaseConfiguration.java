package pl.futurecollars.invoicing.db;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.db.file.IdProvider;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;
import pl.futurecollars.invoicing.db.mongo.MongoBasedDatabase;
import pl.futurecollars.invoicing.db.mongo.MongoIdProvider;
import pl.futurecollars.invoicing.db.sql.SqlDatabase;
import pl.futurecollars.invoicing.db.sql.jpa.InvoiceRepository;
import pl.futurecollars.invoicing.db.sql.jpa.JpaDatabase;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.utils.FilesService;
import pl.futurecollars.invoicing.utils.JsonService;

@Slf4j
@Configuration
public class DatabaseConfiguration {

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "file")
  public IdProvider idProvider(
      FilesService filesService,
      @Value("${invoicing-system.database.directory}") String databaseDirectory,
      @Value("${invoicing-system.database.id.file}") String idFile
  ) throws IOException {
    Path idFilePath = Files.createTempFile(databaseDirectory, idFile);
    return new IdProvider(idFilePath, filesService);
  }

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "file")
  public Database fileBasedDatabase(
      IdProvider idProvider,
      FilesService filesService,
      JsonService jsonService,
      @Value("${invoicing-system.database.directory}") String databaseDirectory,
      @Value("${invoicing-system.database.invoices.file}") String invoicesFile
  ) throws IOException {
    Path databaseFilePath = Files.createTempFile(databaseDirectory, invoicesFile);
    return new FileBasedDatabase(databaseFilePath, idProvider, filesService, jsonService);
  }

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "sql")
  public Database sqlDatabase(JdbcTemplate jdbcTemplate) {
    return new SqlDatabase(jdbcTemplate);
  }

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "memory")
  public Database inMemoryDatabase() {
    return new InMemoryDatabase();
  }

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "jpa")
  public Database jpaDatabase(InvoiceRepository invoiceRepository) {
    return new JpaDatabase(invoiceRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "mongo")
  public MongoDatabase mongoDb(
      @Value("${invoicing-system.database.name}") String databaseName
  ) {
    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    MongoClientSettings settings = MongoClientSettings.builder()
        .codecRegistry(pojoCodecRegistry)
        .build();

    MongoClient client = MongoClients.create(settings);
    return client.getDatabase(databaseName);
  }

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "mongo")
  public MongoIdProvider mongoIdProvider(
      @Value("${invoicing-system.database.counter.collection}") String collectionName,
      MongoDatabase mongoDb
  ) {
    MongoCollection<Document> collection = mongoDb.getCollection(collectionName);
    return new MongoIdProvider(collection);
  }

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "mongo")
  public Database mongoBasedDatabase(@Value("${invoicing-system.database.collection}") String databaseCollection,
                                     MongoDatabase mongoDb,
                                     MongoIdProvider mongoIdProvider) {
    return new MongoBasedDatabase(mongoDb.getCollection(databaseCollection, Invoice.class), mongoIdProvider);
  }

}
