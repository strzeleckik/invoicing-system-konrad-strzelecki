package pl.futurecollars.invoicing.db;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import pl.futurecollars.invoicing.configuration.Configuration;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.FileService;

public class FileDatabase implements Database {

  private final FileService fileService;

  private final Configuration configuration;

  public FileDatabase(FileService fileService, Configuration configuration) {
    this.fileService = fileService;
    this.configuration = configuration;
  }

  @Override
  public void save(Invoice invoice) {
    String uuid = UUID.randomUUID().toString();
    invoice.setId(uuid);

    List<Invoice> invoices = getAll();
    invoices.add(invoice);

    fileService.writeDataToFile(configuration.getDbPath(), invoices);

  }

  @Override
  public Optional<Invoice> getById(String id) {
    return getAll()
        .stream()
        .filter(invoice -> invoice.getId().equals(id))
        .findFirst();
  }

  @Override
  public List<Invoice> getAll() {
    List<Invoice> invoices = fileService.getDataListFromFile(configuration.getDbPath(), Invoice.class);
    return invoices;
  }

  @Override
  public void update(int id, Invoice updatedInvoice) {

  }

  @Override
  public void delete(int id) {

  }
}
