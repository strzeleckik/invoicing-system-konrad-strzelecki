package pl.futurecollars.invoicing.db.sql.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@Slf4j
@RequiredArgsConstructor
public class JpaDatabase implements Database {

  private final InvoiceRepository invoiceRepository;

  @Override
  public int save(Invoice invoice) {
    log.info("save(invoice = {})", invoice);
    return invoiceRepository.save(invoice).getId();
  }

  @Override
  public Optional<Invoice> getById(int id) {
    log.info("getById(invoice = {})", id);
    return invoiceRepository.findById(id);

  }

  @Override
  public List<Invoice> getAll() {
    List<Invoice> invoices = new ArrayList<>();
    invoiceRepository.findAll().forEach(invoices::add);
    return invoices;
  }

  @Override
  public Optional<Invoice> update(int id, Invoice updatedInvoice) {
    Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);

    if (invoiceOptional.isPresent()) {
      Invoice invoice = invoiceOptional.get();
      updatedInvoice.setId(id);
      updatedInvoice.getBuyer().setId(invoice.getBuyer().getId());
      updatedInvoice.getSeller().setId(invoice.getSeller().getId());
      invoiceRepository.save(updatedInvoice);

    }

    return invoiceRepository.findById(id);
  }

  @Override
  public Optional<Invoice> delete(int id) {
    Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
    invoiceOptional.ifPresent(invoiceRepository::delete);
    return invoiceOptional;
  }
}
