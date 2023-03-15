package pl.futurecollars.invoicing.db;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.futurecollars.invoicing.configuration.InvoicingAppConfiguration;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.FileService;

@Slf4j
@ConditionalOnProperty(value = "invoicing-system.database.type", havingValue = "file")
@Repository
@RequiredArgsConstructor
public class FileDatabase implements Database {

  private final FileService fileService;

  private final InvoicingAppConfiguration configuration;

  @Override
  public String save(Invoice invoice) {
    String uuid = UUID.randomUUID().toString();
    invoice.setId(uuid);

    List<Invoice> invoices = getAll();
    log.debug("invoices.size = {}", invoices.size());
    invoices.add(invoice);

    fileService.writeDataToFile(configuration.getDbPath(), invoices);
    return invoice.getId();
  }

  @Override
  public Optional<Invoice> getById(String id) {
    log.info("getById(id = {})", id);
    return getAll()
        .stream()
        .filter(invoice -> invoice.getId().equals(id))
        .findFirst();
  }

  @Override
  public List<Invoice> getAll() {
    return fileService.getDataListFromFile(configuration.getDbPath(), Invoice.class);
  }

  @Override
  public void update(String id, Invoice updatedInvoice) {

    List<Invoice> invoices = getAll();
    invoices
        .stream()
        .filter(invoice -> invoice.getId().equals(id))
        .findFirst()
        .ifPresent(invoice -> {
          invoice.setDate(updatedInvoice.getDate());
          invoice.setBuyer(updatedInvoice.getBuyer());
          invoice.setSeller(updatedInvoice.getSeller());
          invoice.setEntries(updatedInvoice.getEntries());
        });
    fileService.writeDataToFile(configuration.getDbPath(), invoices);
  }

  @Override
  public void delete(String id) {
    List<Invoice> invoices = getAll();
    Iterator<Invoice> i = invoices.iterator();
    while (i.hasNext()) {
      Invoice invoice = i.next();
      if (invoice.getId().equals(id)) {
        i.remove();
      }
    }
    fileService.writeDataToFile(configuration.getDbPath(), invoices);
  }
}
