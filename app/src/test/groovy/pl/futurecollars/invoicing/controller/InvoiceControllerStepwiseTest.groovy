package pl.futurecollars.invoicing.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.futurecollars.invoicing.helpers.TestHelper
import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
@Stepwise
class InvoiceControllerStepwiseTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper

    @Shared
    private String invoiceId;

    @Shared
    private Invoice originalInvoice = TestHelper.createInvoice()


    def "empty array is returned when no invoices were created"(){
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/invoices"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString
        then:
        response == "[ ]"
    }

    def "add single invoice"(){
        given:
        def invoice = originalInvoice
        invoiceId = invoice.getId()
        def invoiceAsJson = objectMapper.writeValueAsString(invoice)

        when:
        def createdInvoiceId = mockMvc.perform(
                MockMvcRequestBuilders.post("/invoices")
                        .content(invoiceAsJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        then:
        createdInvoiceId == invoiceId
    }

    def "one invoice is returned when getting all invoices"() {
        given:
        def expectedInvoice = originalInvoice

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/invoices"))

                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        def invoices = objectMapper.readValue(response, Invoice[])
        then:
        invoices.size() == 1
        invoices[0].id == invoiceId
        invoices[0] == expectedInvoice
    }

    def "invoice is returned correctly when getting by id"() {
        given:
        def expectedInvoice = originalInvoice

        when:
        def response = mockMvc.perform(get("/invoices/" + invoiceId))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        def invoice = objectMapper.readValue(response, Invoice)
        then:
        invoice == expectedInvoice
    }

    def "invoice can be deleted"() {
        expect:
        mockMvc.perform(delete("/invoices/" + invoiceId))
                .andExpect(status().isOk())
    }

    def "404 will be returned when invoice is not found"() {
        expect:
        mockMvc.perform(get("/invoices/" + invoiceId))
                .andExpect(status().isNotFound())
    }
}
