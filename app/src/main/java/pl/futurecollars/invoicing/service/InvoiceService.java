package pl.futurecollars.invoicing.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@RequiredArgsConstructor
public class InvoiceService {

  private final Database database;

  public void save(Invoice invoice) {
    database.save(invoice);
  }
  public Optional<Invoice> getById(String id) {
    return database.getById(id);
  }
  public List<Invoice> getAll() {
    return database.getAll();
  }

  public void update(String id, Invoice updatedInvoice) {
    database.update(id, updatedInvoice);
  }

  public void delete(String id) {
    database.delete(id);
  }

}
