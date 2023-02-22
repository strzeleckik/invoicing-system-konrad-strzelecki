package pl.futurecollars.invoicing.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.futurecollars.invoicing.helpers.TestHelpers
import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class InvoiceControllerTest extends Specification {

    private static final String ENDPOINT = "/invoices"

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    def "empty array is returned when no invoices were created"(){
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
            .andExpect(status().isOk())
            .andReturn()
            .response
            .contentAsString
        then:
        response == "[ ]"
    }

    def "404 is returned when invoice id is not found when getting invoice by id [#id]"() {
        given:
        def expectedInvoices = (1..30).collect({ addInvoiceAndReturnId(TestHelpers.createInvoice())})

        expect:
        mockMvc.perform(
                get("$ENDPOINT/$id")
        )
                .andExpect(status().isNotFound())


        where:
        id << [-100, -2, -1, 0, 168, 1256]
    }

    def "invoice date can be modified"() {
        given:
        def newInvoice = TestHelpers.createInvoice()
        def id = addInvoiceAndReturnId(newInvoice)
        def updatedInvoice = newInvoice
        updatedInvoice.setSeller("new seller")
        updatedInvoice.setBuyer("new buyer")
        expect:
        mockMvc.perform(
                put("$ENDPOINT/$id")
                        .content(objectMapper.writeValueAsString(updatedInvoice))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())

        getInvoiceById(id) == updatedInvoice
    }

    private Invoice getInvoiceById(String id) {
        def invoiceAsString = mockMvc.perform(get("$ENDPOINT/$id"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        objectMapper.readValue(invoiceAsString, Invoice)
    }

    private String addInvoiceAndReturnId(Invoice invoice) {
        def invoiceAsJson = objectMapper.writeValueAsString(invoice)

        return mockMvc.perform(
                        post(ENDPOINT)
                                .content(invoiceAsJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                        .andExpect(status().isOk())
                        .andReturn()
                        .response
                        .contentAsString

    }
}
