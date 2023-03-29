package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "Invoice id (generated by application)", required = true, example = "1")
  private Integer id;

  @ApiModelProperty(value = "Invoice number (assigned by user)", required = true, example = "2020/03/08/0000001")
  private String number;

  @ApiModelProperty(value = "Date invoice was created", required = true)
  private LocalDate date;

  @OneToOne(cascade = CascadeType.ALL)
  @ApiModelProperty(value = "Company who bought the product/service", required = true)
  private Company buyer;

  @OneToOne(cascade = CascadeType.ALL)
  @ApiModelProperty(value = "Company who is selling the product/service", required = true)
  private Company seller;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @ApiModelProperty(value = "List of products/services", required = true)
  private List<InvoiceEntry> entries;

}
