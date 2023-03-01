package pl.futurecollars.invoicing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.futurecollars.invoicing.model.Invoice;
@RequestMapping("invoices")
@Api(tags = "invoice-controller")
public interface InvoiceApi {
  @GetMapping
  @ApiOperation(value = "get list of all invoices")
  ResponseEntity<List<Invoice>> getAllInvoices();

  @GetMapping("/{id}")
  ResponseEntity<Invoice> getById(@PathVariable String id);

  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteById(@PathVariable String id);

  @PostMapping
  ResponseEntity<String> add(@RequestBody Invoice invoice);

  @PutMapping("/{id}")
  ResponseEntity<?> update(@PathVariable String id, @RequestBody Invoice invoice);
}
