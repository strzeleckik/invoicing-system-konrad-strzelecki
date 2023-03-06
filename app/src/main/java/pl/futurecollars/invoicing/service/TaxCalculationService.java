package pl.futurecollars.invoicing.service;


import java.math.BigDecimal;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.dto.TaxCalculatorDto;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaxCalculationService {

  private final Database database;

  public TaxCalculatorDto getTaxCalculations(String taxId) {
    return TaxCalculatorDto.builder()
        .income(income(taxId))
        .costs(costs(taxId))
        .build();
  }

  public BigDecimal income(String taxIdentificationNumber) {
    return database.visit(sellerPredicate(taxIdentificationNumber), InvoiceEntry::getPrice);
  }

  public BigDecimal costs(String taxIdentificationNumber) {
    return database.visit(buyerPredicate(taxIdentificationNumber), InvoiceEntry::getPrice);
  }


  private Predicate<Invoice> sellerPredicate(String taxIdentificationNumber) {
    return invoice -> taxIdentificationNumber.equals(invoice.getSeller().getTaxIdentificationNumber());
  }

  private Predicate<Invoice> buyerPredicate(String taxIdentificationNumber) {
    return invoice -> taxIdentificationNumber.equals(invoice.getBuyer().getTaxIdentificationNumber());
  }
}
