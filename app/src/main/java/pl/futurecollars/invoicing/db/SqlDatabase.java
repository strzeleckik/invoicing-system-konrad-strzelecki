package pl.futurecollars.invoicing.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;


@Slf4j
@ConditionalOnProperty(value = "invoicing-system.database.type", havingValue = "sql")
@Repository
@RequiredArgsConstructor
public class SqlDatabase implements Database {

  private final JdbcTemplate jdbcTemplate;

  @Override
  @Transactional
  public String save(Invoice invoice) {
    log.info("SqlDatabase save(invoice = {})", invoice);
    String uuid = String.valueOf(UUID.randomUUID());
//    jdbcTemplate.update("insert into company (id, tax_id, address, name) values (5, '123', 'lipowa 55', 'Super company 2')");
//    jdbcTemplate.update("insert into invoice (id, date, number, buyer, seller) values ('" + uuid + "', '2023-03-15', 'FV/11/03/2023', 1, 10)");

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement("insert into invoice (id, date, number, buyer, seller) values (?, ?, ?, ?, ?)");
      ps.setString(1, uuid);
      ps.setDate(2, Date.valueOf(invoice.getDate()));
      ps.setString(3, invoice.getNumber());
      ps.setLong(4, invoice.getBuyer().getId());
      ps.setLong(5, invoice.getSeller().getId());
      return ps;
    });

    return uuid;
  }

  @Override
  public Optional<Invoice> getById(String id) {
    return Optional.empty();
  }

  @Override
  public List<Invoice> getAll() {
    log.info("sqlDatabase getAll()");

    return jdbcTemplate.query("select i.id, i.date, i.number, \n" +
        "s.id as seller_id, s.tax_id as seller_tax_id, s.address as seller_address, s.name as seller_name, \n" +
        "b.id as buyer_id, b.tax_id as buyer_tax_id, b.address as buyer_address, b.name as buyer_name \n" +
        "from invoice i \n" +
        "inner join company s on i.seller = s.id\n" +
        "inner join company b on i.buyer = b.id", (rs, rowNr) ->
        Invoice.builder()
            .id(rs.getString("id"))
            .number(rs.getString("number"))
            .date(rs.getDate("date").toLocalDate())
            .seller(
                Company.builder()
                    .address(rs.getString("seller_address"))
                    .name(rs.getString("seller_name"))
                    .taxIdentificationNumber(rs.getString("seller_tax_id"))
                    .id(rs.getLong("seller_id"))
                    .build()
            )
            .buyer(
                Company.builder()
                    .address(rs.getString("buyer_address"))
                    .name(rs.getString("buyer_name"))
                    .taxIdentificationNumber(rs.getString("buyer_tax_id"))
                    .id(rs.getLong("buyer_id"))
                    .build()
            )
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
