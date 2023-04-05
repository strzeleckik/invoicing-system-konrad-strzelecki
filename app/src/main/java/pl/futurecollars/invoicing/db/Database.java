package pl.futurecollars.invoicing.db;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;

public interface Database<T extends WithId> {

  int save(T item);

  Optional<T> getById(int id);

  List<T> getAll();

  Optional<T> update(int id, T updatedItem);

  Optional<T> delete(int id);

  default void reset() {
    getAll().forEach(item -> delete(item.getId()));
  }
}
