package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

  private String id;

  private String number;

  @ApiModelProperty(value = "operation date", example = "2023-02-28")
  private LocalDate date;

  @ApiModelProperty(value = "company that sells", required = true, example = "Github")
  private Company seller;
  private Company buyer;

  private List<InvoiceEntry> entries;
}
