package pl.futurecollars.invoicing.db;

import java.util.List;
import java.util.Optional;
import pl.futurecollars.invoicing.model.Invoice;

public interface Database {

  String save(Invoice invoice);

  Optional<Invoice> getById(String id);

  List<Invoice> getAll();

  void update(String id, Invoice updatedInvoice);

  void delete(String id);

}
