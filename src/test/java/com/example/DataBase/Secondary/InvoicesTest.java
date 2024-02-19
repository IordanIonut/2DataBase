package com.example.DataBase.Secondary;

import com.example.DataBase.Secondary.Model.Invoices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class InvoicesTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Invoices> invoices;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        invoices = Arrays.asList(
                Invoices.builder().id_invoice(1L).number(111).date(LocalDateTime.parse("2024-12-31T23:59:59")).amount(11.1).details("asdsaadsa1").build(),
                Invoices.builder().id_invoice(2L).number(222).date(LocalDateTime.parse("2024-12-31T23:59:59")).amount(22.2).details("asdsaadsa2").build(),
                Invoices.builder().id_invoice(3L).number(333).date(LocalDateTime.parse("2024-12-31T23:59:59")).amount(33.3).details("asdsaadsa3").build());
    }

    @Test
    public void testSaveInvoices() throws Exception {
        mvc.perform(post("/api/invoices/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoices.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_invoice").value(invoices.get(0).getId_invoice().longValue()))
                .andExpect(jsonPath("$.number").value(invoices.get(0).getNumber()))
                .andExpect(jsonPath("$.date").value(invoices.get(0).getDate().toString()))
                .andExpect(jsonPath("$.amount").value(invoices.get(0).getAmount()))
                .andExpect(jsonPath("$.details").value(invoices.get(0).getDetails()));

        mvc.perform(post("/api/invoices/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoices.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_invoice").value(invoices.get(1).getId_invoice().longValue()))
                .andExpect(jsonPath("$.number").value(invoices.get(1).getNumber()))
                .andExpect(jsonPath("$.date").value(invoices.get(1).getDate().toString()))
                .andExpect(jsonPath("$.amount").value(invoices.get(1).getAmount()))
                .andExpect(jsonPath("$.details").value(invoices.get(1).getDetails()));

        mvc.perform(post("/api/invoices/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoices.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_invoice").value(invoices.get(2).getId_invoice().longValue()))
                .andExpect(jsonPath("$.number").value(invoices.get(2).getNumber()))
                .andExpect(jsonPath("$.date").value(invoices.get(2).getDate().toString()))
                .andExpect(jsonPath("$.amount").value(invoices.get(2).getAmount()))
                .andExpect(jsonPath("$.details").value(invoices.get(2).getDetails()));
    }

    @Test
    public void testGetAllInvoices() throws Exception{
        mvc.perform(get("/api/invoices")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testByIdInvoices() throws Exception {
        mvc.perform(get("/api/invoices/id?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_invoice").value(invoices.get(0).getId_invoice().longValue()))
                .andExpect(jsonPath("$[0].number").value(invoices.get(0).getNumber()))
                .andExpect(jsonPath("$[0].date").value(invoices.get(0).getDate().toString()))
                .andExpect(jsonPath("$[0].amount").value(invoices.get(0).getAmount()))
                .andExpect(jsonPath("$[0].details").value(invoices.get(0).getDetails()));
    }

    @Test
    public void testDeleteInvoices() throws Exception{
        mvc.perform(delete("/api/invoices/id?id=4")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateInvoices() throws Exception{
        mvc.perform(put("/api/invoices/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Invoices.builder()
                        .id_invoice(2L)
                        .number(1111)
                        .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                        .amount(11.1)
                        .details("asdsaadsa1")
                        .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_invoice").value(2L))
                .andExpect(jsonPath("$.number").value(1111))
                .andExpect(jsonPath("$.date").value("2024-12-31T23:59:59"))
                .andExpect(jsonPath("$.amount").value(11.1))
                .andExpect(jsonPath("$.details").value("asdsaadsa1"));
    }

    @Test
    public void testInvoicesNumberNull() throws Exception{
        result = mvc.perform(post("/api/invoices/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Invoices.builder()
                        .id_invoice(1L)
                        .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                        .amount(11.1)
                        .details("asdsaadsa1")
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Number"));
    }

    @Test
    public void testInvoicesDateNull() throws Exception{
        result = mvc.perform(post("/api/invoices/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Invoices.builder()
                                .id_invoice(1L)
                                .number(1111)
                                .amount(11.1)
                                .details("asdsaadsa1")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Date"));
    }

    @Test
    public void testInvoicesAmountNull() throws Exception{
        result = mvc.perform(post("/api/invoices/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Invoices.builder()
                                .id_invoice(1L)
                                .number(1111)
                                .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .details("asdsaadsa1")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Amount"));
    }

    @Test
    public void testInvoicesDetails() throws Exception{
        result = mvc.perform(post("/api/invoices/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Invoices.builder()
                                .id_invoice(1L)
                                .number(1111)
                                .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .amount(11.1)
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Details"));
    }
}
