import java.time.LocalDate;
import java.util.List;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;

public class Test {
  public static void main(String[] args) {

    Invoice invoice = new Invoice();
    invoice.setDate(LocalDate.now());
    invoice.setEntries(List.of());
    invoice.setSeller(new Company());

    Invoice invoice1 = new Invoice(null, LocalDate.now(), new Company(), null, List.of());


    Invoice invoice2 = Invoice.builder()
        .date(LocalDate.now())
        .seller(new Company())
        .entries(List.of())
        .build();


  }
}
