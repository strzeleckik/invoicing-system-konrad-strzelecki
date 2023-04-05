package pl.futurecollars.invoicing.db.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.db.WithId;
import pl.futurecollars.invoicing.model.Invoice;

public class InMemoryDatabase<T extends WithId> implements Database<T> {

  private final Map<Integer, T> items = new HashMap<>();
  private int nextId = 1;

  @Override
  public int save(T item) {
    item.setId(nextId);
    items.put(nextId, item);

    return nextId++;
  }

  @Override
  public Optional<T> getById(int id) {
    return Optional.ofNullable(items.get(id));
  }

  @Override
  public List<T> getAll() {
    return new ArrayList<>(items.values());
  }

  @Override
  public Optional<T> update(int id, T updatedItem) {
    updatedItem.setId(id);

    return Optional.ofNullable(items.put(id, updatedItem));
  }

  @Override
  public Optional<T> delete(int id) {
    return Optional.ofNullable(items.remove(id));
  }
}
