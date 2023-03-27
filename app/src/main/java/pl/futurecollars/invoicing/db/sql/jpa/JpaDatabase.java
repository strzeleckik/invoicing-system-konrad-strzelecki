package pl.futurecollars.invoicing.db.sql.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.db.sql.InvoiceRepository;
import pl.futurecollars.invoicing.model.Invoice;

@RequiredArgsConstructor
public class JpaDatabase implements Database {

  private final InvoiceRepository invoiceRepository;

  @Override
  public int save(Invoice invoice) {
    return invoiceRepository.save(invoice).getId();
  }

  @Override
  public Optional<Invoice> getById(int id) {
    return invoiceRepository.findById(id);
  }

  @Override
  public List<Invoice> getAll() {
    List<Invoice> result = new ArrayList<>();
    invoiceRepository.findAll().forEach(
        invoice -> result.add(invoice)
    );

    return result;
  }

  @Override
  public Optional<Invoice> update(int id, Invoice updatedInvoice) {
    Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
    if(invoiceOpt.isPresent()){
      Invoice invoice = invoiceOpt.get();

      updatedInvoice.setId(id);
      updatedInvoice.getBuyer().setId(invoice.getBuyer().getId());
      updatedInvoice.getSeller().setId(invoice.getSeller().getId());

      invoiceRepository.save(updatedInvoice);
    }

    return Optional.empty();
  }

  @Override
  public Optional<Invoice> delete(int id) {
    Optional<Invoice> invoice = invoiceRepository.findById(id);
    invoice.ifPresent(invoiceRepository::delete);
    return invoice;
  }
}
