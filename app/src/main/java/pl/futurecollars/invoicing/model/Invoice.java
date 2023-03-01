package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModel;
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
  private LocalDate date;
  @ApiModelProperty(value = "name of seller company", required = true, example = "Microsoft")
  private String seller;
  private String buyer;

  private List<InvoiceEntry> entries;
}
