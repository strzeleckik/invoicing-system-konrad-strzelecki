package pl.futurecollars.invoicing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;


@Slf4j
@RestController
@RequestMapping("invoices")
@RequiredArgsConstructor
public class InvoiceController implements InvoiceApi {

  private final InvoiceService invoiceService;

  @Override
  @GetMapping
  public ResponseEntity<List<Invoice>> getAllInvoices() {
    log.info("controller getAllInvoices)");
    return ResponseEntity.ok(invoiceService.getAllInvoices());
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<Invoice> getById(@PathVariable String id) {
    log.info("controller getById(id = {})", id);
    return invoiceService.getById(id)
        .map(invoice -> ResponseEntity.ok().body(invoice))
        .orElse(ResponseEntity.notFound().build());
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteById(@PathVariable String id) {
    log.info("controller deleteById(id = {})", id);
    invoiceService.delete(id);
    return ResponseEntity.ok("ok");
  }

  @Override
  @PostMapping
  public ResponseEntity<String> add(@RequestBody Invoice invoice) {
    log.info("controller add(invoice = {})", invoice);
    return ResponseEntity.ok(invoiceService.save(invoice));
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable String id, @RequestBody Invoice invoice) {
    log.info("controller getById(id = {})", id);
    invoiceService.update(id, invoice);
    return ResponseEntity.ok("ok");

  }

}
