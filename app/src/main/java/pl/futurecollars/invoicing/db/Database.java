package pl.futurecollars.invoicing.db;

import java.util.List;
import java.util.Optional;
import pl.futurecollars.invoicing.model.Invoice;

public interface Database {

  void save(Invoice invoice);

  Optional<Invoice> getById(String id);

  List<Invoice> getAll();

  void update(int id, Invoice updatedInvoice);

  void delete(int id);

}
