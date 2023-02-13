package pl.futurecollars.invoicing.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import pl.futurecollars.invoicing.model.Invoice;

@Repository
public class InMemoryDatabase implements Database {

  private List<Invoice> invoices = new ArrayList<>();

  @Override
  public String save(Invoice invoice) {
    invoice.setId(UUID.randomUUID().toString());
    invoices.add(invoice);
    return invoice.getId();
  }

  @Override
  public Optional<Invoice> getById(String id) {
    return invoices.stream()
        .filter(invoice -> invoice.getId().equals(id))
        .findFirst();
  }

  @Override
  public List<Invoice> getAll() {
    return invoices;
  }

  @Override
  public void update(String id, Invoice updatedInvoice) {
    getById(id).ifPresent(invoice -> {
      invoice.setEntries(updatedInvoice.getEntries());
      invoice.setBuyer(updatedInvoice.getBuyer());
      invoice.setDate(updatedInvoice.getDate());
      invoice.setSeller(updatedInvoice.getSeller());
    });
  }

  @Override
  public void delete(String id) {
    getById(id).ifPresent(invoice -> invoices.remove(invoice));
  }
}
