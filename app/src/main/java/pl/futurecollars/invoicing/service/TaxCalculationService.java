package pl.futurecollars.invoicing.service;


import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.dto.TaxCalculationDto;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaxCalculationService {

  private final Database database;

  public TaxCalculationDto prepareTaxCalculationDto(String taxId){
    log.info("prepareTaxCalculationDto(taxId = {})", taxId);
    return TaxCalculationDto.builder()
        .income(database.visit(sellerPredicate(taxId), InvoiceEntry::getPrice))
        .incomingVat(database.visit(sellerPredicate(taxId), InvoiceEntry::getVatValue))
        .cost(database.visit(buyerPredicate(taxId), InvoiceEntry::getPrice))
        .build();
  }

  private Predicate<Invoice> sellerPredicate(String taxId) {
    return invoice -> taxId.equals(invoice.getSeller().getTaxIdentificationNumber());
  }

  private Predicate<Invoice> buyerPredicate(String taxId) {
    return invoice -> taxId.equals(invoice.getBuyer().getTaxIdentificationNumber());
  }

}
