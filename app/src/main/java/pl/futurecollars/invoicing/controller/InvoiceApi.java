package pl.futurecollars.invoicing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Api(tags = {"invoice-controller"})

public interface InvoiceApi {

  @ApiOperation(value = "API to list all invoices in the system")
  ResponseEntity<List<Invoice>> getAllInvoices();

  @ApiOperation(value = "API to get invoice by ID")
  @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Invoice not found")})
  ResponseEntity<Invoice> getById(@PathVariable String id);

  ResponseEntity<?> deleteById(@PathVariable String id);

  ResponseEntity<String> add(@RequestBody Invoice invoice);

  ResponseEntity<?> update(@PathVariable String id, @RequestBody Invoice invoice);
}
