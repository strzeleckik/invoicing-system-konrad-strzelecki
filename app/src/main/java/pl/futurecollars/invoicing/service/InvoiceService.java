package pl.futurecollars.invoicing.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@Service
@RequiredArgsConstructor
public class InvoiceService {

  private final Database database;

  public List<Invoice> getAllInvoices() {
    return database.getAll();
  }

  public Optional<Invoice> getById(String id) {
    return database.getById(id);
  }

  public void delete(String id) {
    database.delete(id);
  }

  public String save(Invoice invoice) {
    return database.save(invoice);
  }

  public void update(String id, Invoice invoice) {
    database.update(id, invoice);
  }
}
