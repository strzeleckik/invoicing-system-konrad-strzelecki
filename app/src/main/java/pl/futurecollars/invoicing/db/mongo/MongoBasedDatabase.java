package pl.futurecollars.invoicing.db.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.swagger.models.auth.In;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.bson.Document;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@AllArgsConstructor
public class MongoBasedDatabase implements Database {

  private MongoCollection<Invoice> collection;

  private final MongoIdProvider idProvider;

  @Override
  public int save(Invoice invoice) {
    invoice.setId(idProvider.getNextIdAndIncrement());
    collection.insertOne(invoice);
    return invoice.getId();
  }

  @Override
  public Optional<Invoice> getById(int id) {
    return Optional.ofNullable(
        collection.find(new Document("_id", id)).first()
    );
  }

  @Override
  public List<Invoice> getAll() {
    return StreamSupport
        .stream(collection.find().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Invoice> update(int id, Invoice updatedInvoice) {
    updatedInvoice.setId(id);
    return Optional.ofNullable(
        collection.findOneAndReplace(new Document("_id", id), updatedInvoice)
    );
  }

  @Override
  public Optional<Invoice> delete(int id) {
    return Optional.ofNullable(
        collection.findOneAndDelete(new Document("_id", id))
    );
  }
}
