package pl.futurecollars.invoicing.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.configuration.Configuration;
import pl.futurecollars.invoicing.db.FileDatabase;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.FileService;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
@RequestMapping("invoices")
public class InvoiceController {

  private InvoiceService invoiceService = new InvoiceService(new FileDatabase(new FileService(), new Configuration("invoices.json")));

  @GetMapping
  public List<Invoice> getAll() {
    return invoiceService.getAll();
  }

  @PostMapping
  public void add(@RequestBody Invoice invoice) {
    invoiceService.save(invoice);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Invoice> getById(@PathVariable String id) {
    return invoiceService.getById(id)
        .map(invoice -> ResponseEntity.ok().body(invoice))
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteById(@PathVariable String id) {
    invoiceService.delete(id);
    return ResponseEntity.ok("ok");
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable String id, @RequestBody Invoice invoice) {
    invoiceService.update(id, invoice);
    return ResponseEntity.ok("ok");

  }

}
