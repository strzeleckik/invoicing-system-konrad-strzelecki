package pl.futurecollars.invoicing.db;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.futurecollars.invoicing.model.Invoice;


@ConditionalOnProperty(value = "invoicing-system.database.type", havingValue = "sql")
@Repository
@RequiredArgsConstructor
public class SqlDatabase implements Database {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public String save(Invoice invoice) {
    String uuid = String.valueOf(UUID.randomUUID());
    jdbcTemplate.update("insert into invoice (id, date, number, buyer, seller) values ('" + uuid + "','2021-03-05' , '2021/03/05/0001', 1, 2);");
    return uuid;
  }

  @Override
  public Optional<Invoice> getById(String id) {
    return Optional.empty();
  }

  @Override
  public List<Invoice> getAll() {

    return jdbcTemplate.query(" select * from invoice" , (rs, rowNr) ->
      Invoice.builder()
          .date(rs.getDate("date").toLocalDate())
          .id(rs.getString("id"))
          .number(rs.getString("number"))
          .build());

  }

  @Override
  public void update(String id, Invoice updatedInvoice) {

  }

  @Override
  public void delete(String id) {
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement("delete from invoice where id = ?");
      ps.setString(1, id);
      return ps;
    });

  }
}
