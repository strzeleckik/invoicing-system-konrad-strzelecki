package pl.futurecollars.invoicing.controller;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
@RequestMapping("invoices")
@RequiredArgsConstructor
public class InvoiceController {

  private final InvoiceService invoiceService;

  @GetMapping("/hello")
  public ResponseEntity<String> sayHello(@RequestParam(required = false) String name, @RequestParam int age) {
    if (name == null) {
      return ResponseEntity.badRequest().body("name parameter is required");
    }
    int yearBorn = LocalDate.now().getYear() - age;

    return ResponseEntity.ok("Hello " + name + "! Your name has " + name.length() + " letters, you where born in " + yearBorn);
  }

  @GetMapping
  public ResponseEntity<List<Invoice>> getAllInvoices() {
    return ResponseEntity.ok(invoiceService.getAllInvoices());
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

  @PostMapping
  public ResponseEntity<String> add(@RequestBody Invoice invoice) {
    return ResponseEntity.ok(invoiceService.save(invoice));
  }

  //  @PutMapping("/{id}")
  //  public ResponseEntity<?> update(@PathVariable String id, @RequestBody Invoice invoice) {
  //    invoiceService.update(id, invoice);
  //    return ResponseEntity.ok("ok");
  //
  //  }

}
