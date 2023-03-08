package pl.futurecollars.invoicing.service;

import java.math.BigDecimal;
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

  public TaxCalculationDto prepareTaxCalculationDto(String taxIdentificationNumber) {
    return TaxCalculationDto.builder()
        .income(income(taxIdentificationNumber))
        .costs(costs(taxIdentificationNumber))
        .earnings(getEarnings(taxIdentificationNumber))
        .incomingVat(incomingVat(taxIdentificationNumber))
        .outgoingVat(outgoingVat(taxIdentificationNumber))
        .vatToReturn(getVatToReturn(taxIdentificationNumber))
        .build();
  }

  public BigDecimal income(String taxIdentificationNumber) {
    return database.visit(sellerPredicate(taxIdentificationNumber), InvoiceEntry::getPrice);
  }

  public BigDecimal costs(String taxIdentificationNumber) {
    return database.visit(buyerPredicate(taxIdentificationNumber), InvoiceEntry::getPrice);
  }

  public BigDecimal incomingVat(String taxIdentificationNumber) {
    return database.visit(sellerPredicate(taxIdentificationNumber), InvoiceEntry::getVatValue);
  }

  public BigDecimal outgoingVat(String taxIdentificationNumber) {
    return database.visit(buyerPredicate(taxIdentificationNumber), InvoiceEntry::getVatValue);
  }

  public BigDecimal getEarnings(String taxIdentificationNumber) {
    return income(taxIdentificationNumber).subtract(costs(taxIdentificationNumber));
  }

  public BigDecimal getVatToReturn(String taxIdentificationNumber) {
    return incomingVat(taxIdentificationNumber).subtract(outgoingVat(taxIdentificationNumber));
  }

  private Predicate<Invoice> sellerPredicate(String taxId) {
    return invoice -> taxId.equals(invoice.getSeller().getTaxIdentificationNumber());
  }

  private Predicate<Invoice> buyerPredicate(String taxId) {
    return invoice -> taxId.equals(invoice.getBuyer().getTaxIdentificationNumber());
  }

}
