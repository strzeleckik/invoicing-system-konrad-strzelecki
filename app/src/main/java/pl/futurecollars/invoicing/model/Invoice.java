package pl.futurecollars.invoicing.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

  private Long id;
  private LocalDateTime date;
  private Company seller;
  private Company buyer;
  List<InvoiceEntry> entries;

}
