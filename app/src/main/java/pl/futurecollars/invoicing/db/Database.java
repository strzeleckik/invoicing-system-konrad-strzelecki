package pl.futurecollars.invoicing.db;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;

public interface Database {

  String save(Invoice invoice);

  Optional<Invoice> getById(String id);

  List<Invoice> getAll();

  void update(String id, Invoice updatedInvoice);

  void delete(String id);

  default BigDecimal visit(Predicate<Invoice> predicate, Function<InvoiceEntry, BigDecimal> invoiceEntryToAmount){
    return getAll().stream()
        .filter(predicate)
        .flatMap(invoice -> invoice.getEntries().stream())
        .map(invoiceEntryToAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
