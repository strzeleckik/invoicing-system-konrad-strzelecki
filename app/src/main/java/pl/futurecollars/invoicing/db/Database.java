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

  default BigDecimal visit(Predicate<Invoice> invoicePredicate, Function<InvoiceEntry, BigDecimal> invoiceEntryToValue) {
    return getAll().stream()
        .filter(invoicePredicate)
        .flatMap(i -> i.getEntries().stream())
        .map(invoiceEntryToValue)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
