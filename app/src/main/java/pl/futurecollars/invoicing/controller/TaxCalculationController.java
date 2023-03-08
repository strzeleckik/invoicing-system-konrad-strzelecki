package pl.futurecollars.invoicing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.dto.TaxCalculationDto;
import pl.futurecollars.invoicing.service.TaxCalculationService;

@Slf4j
@RestController
@RequestMapping("tax")
@RequiredArgsConstructor
public class TaxCalculationController {

  private final TaxCalculationService taxCalculationService;

  @GetMapping("/{taxId}")
  public ResponseEntity<TaxCalculationDto> getTaxCalculationDto(@PathVariable("taxId") String taxId) {
    log.info("getTaxCalculationDto(taxId = {})", taxId);
    return ResponseEntity.ok(taxCalculationService.prepareTaxCalculationDto(taxId));
  }

}
