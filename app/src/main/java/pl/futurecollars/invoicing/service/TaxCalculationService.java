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

  private static final BigDecimal TAX_RATE = new BigDecimal(0.19);

  public TaxCalculationDto prepareTaxCalculationDto(String taxIdentificationNumber) {

    BigDecimal earnings = getEarnings(taxIdentificationNumber);
    BigDecimal taxToPay = calculateTaxToPay(earnings);
    BigDecimal netIncome = calculateNetIncome(earnings, taxToPay);

    return TaxCalculationDto.builder()
        .income(income(taxIdentificationNumber))
        .costs(costs(taxIdentificationNumber))
        .earnings(earnings)
        .incomingVat(incomingVat(taxIdentificationNumber))
        .outgoingVat(outgoingVat(taxIdentificationNumber))
        .vatToReturn(getVatToReturn(taxIdentificationNumber))
        .taxToPay(taxToPay)
        .netIncome(netIncome)
        .build();
  }

  public BigDecimal calculateTaxToPay(BigDecimal earnings) {
    return earnings.multiply(TAX_RATE);
  }

  public BigDecimal calculateNetIncome(BigDecimal earnings, BigDecimal taxToPay){
    return earnings.subtract(taxToPay);
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
    return outgoingVat(taxIdentificationNumber).subtract(incomingVat(taxIdentificationNumber));
  }

  private Predicate<Invoice> sellerPredicate(String taxId) {
    return invoice -> taxId.equals(invoice.getSeller().getTaxIdentificationNumber());
  }

  private Predicate<Invoice> buyerPredicate(String taxId) {
    return invoice -> taxId.equals(invoice.getBuyer().getTaxIdentificationNumber());
  }

}
