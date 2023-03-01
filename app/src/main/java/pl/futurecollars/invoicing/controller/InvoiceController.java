package pl.futurecollars.invoicing.controller;

import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InvoiceController implements InvoiceApi {

  private final InvoiceService invoiceService;

  @Override
  public ResponseEntity<List<Invoice>> getAllInvoices() {
    return ResponseEntity.ok(invoiceService.getAllInvoices());
  }

  @Override
  public ResponseEntity<Invoice> getById(@PathVariable String id) {
    log.info("controller getById(id = {})", id);
    log.debug("controller getById(id = {})", id);
    log.warn("controller getById(id = {})", id);
    log.error("controller getById(id = {})", id);
    log.trace("controller getById(id = {})", id);
    return invoiceService.getById(id)
        .map(invoice -> ResponseEntity.ok().body(invoice))
        .orElse(ResponseEntity.notFound().build());
  }

  @Override
  public ResponseEntity<?> deleteById(@PathVariable String id) {
    invoiceService.delete(id);
    return ResponseEntity.ok("ok");
  }

  @Override
  public ResponseEntity<String> add(@RequestBody Invoice invoice) {
    return ResponseEntity.ok(invoiceService.save(invoice));
  }

  @Override
  public ResponseEntity<?> update(@PathVariable String id, @RequestBody Invoice invoice) {
    invoiceService.update(id, invoice);
    return ResponseEntity.ok("ok");

  }

}
