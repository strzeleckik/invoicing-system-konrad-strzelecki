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
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class InvoiceControllerTest extends Specification {

    private static final String ENDPOINT = "/invoices"

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //showing how we can use one test with many parameter variants
    def "example test calculating maximum of two numbers"(int a, int b, int c) {
        expect:
        Math.max(a, b) == c

        //test will be run 3 times because we have 3 variants prepared in where:
        where:
        a | b | c
        1 | 3 | 3
        7 | 4 | 7
        0 | 0 | 0
    }

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
        (1..30).collect({ addInvoiceAndReturnId(TestHelper.createInvoice())})

        expect:
        mockMvc.perform(
                get("$ENDPOINT/$id") // $id is parameter from array of arguments in where:
        )
                .andExpect(status().isNotFound())

        //test will be called as many times as arguments in array below
        where:
        id << ["aa", "bbn", "cc", "dd", "ee", "ff", "gg", "hh"]
    }

    def "invoice date can be modified"() {
        given:
        def newInvoice = TestHelper.createInvoice()
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