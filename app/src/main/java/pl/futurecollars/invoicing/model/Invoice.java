package pl.futurecollars.invoicing.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Invoice {

  private Long id;
  private LocalDateTime date;
  private String creatorName;
  private String destinationCompanyName;

}
