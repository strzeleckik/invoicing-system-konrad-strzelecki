package pl.futurecollars.invoicing.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TaxCalculationDto {

  private final BigDecimal income;
  private final BigDecimal costs;
  private final BigDecimal earnings;

  private final BigDecimal incomingVat;
  private final BigDecimal outgoingVat;
  private final BigDecimal vatToReturn;


}
