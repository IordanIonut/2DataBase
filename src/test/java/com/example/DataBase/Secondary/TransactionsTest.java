package com.example.DataBase.Secondary;

import com.example.DataBase.Secondary.Model.Transactions;
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
public class TransactionsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Transactions> transactions;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        transactions = Arrays.asList(
                Transactions.builder().id_transaction(1L).type("asdasd1").amount(122.1).date(LocalDateTime.parse("2024-12-31T23:59:59")).details("Adsadasdsadad1").build(),
                Transactions.builder().id_transaction(2L).type("asdasd2").amount(122.2).date(LocalDateTime.parse("2024-12-31T23:59:59")).details("Adsadasdsadad2").build(),
                Transactions.builder().id_transaction(3L).type("asdasd3").amount(122.3).date(LocalDateTime.parse("2024-12-31T23:59:59")).details("Adsadasdsadad3").build());
    }

    @Test
    public void testSaveTransactions() throws Exception {
        mvc.perform(post("/api/transactions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactions.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_transaction").value(transactions.get(0).getId_transaction().longValue()))
                .andExpect(jsonPath("$.type").value(transactions.get(0).getType()))
                .andExpect(jsonPath("$.amount").value(transactions.get(0).getAmount()))
                .andExpect(jsonPath("$.date").value(transactions.get(0).getDate().toString()))
                .andExpect(jsonPath("$.details").value(transactions.get(0).getDetails()));

        mvc.perform(post("/api/transactions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactions.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_transaction").value(transactions.get(1).getId_transaction().longValue()))
                .andExpect(jsonPath("$.type").value(transactions.get(1).getType()))
                .andExpect(jsonPath("$.amount").value(transactions.get(1).getAmount()))
                .andExpect(jsonPath("$.date").value(transactions.get(1).getDate().toString()))
                .andExpect(jsonPath("$.details").value(transactions.get(1).getDetails()));

        mvc.perform(post("/api/transactions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactions.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_transaction").value(transactions.get(2).getId_transaction().longValue()))
                .andExpect(jsonPath("$.type").value(transactions.get(2).getType()))
                .andExpect(jsonPath("$.amount").value(transactions.get(2).getAmount()))
                .andExpect(jsonPath("$.date").value(transactions.get(2).getDate().toString()))
                .andExpect(jsonPath("$.details").value(transactions.get(2).getDetails()));
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        mvc.perform(get("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testByIdTransactions() throws Exception {
        mvc.perform(get("/api/transactions/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_transaction").value(transactions.get(0).getId_transaction().longValue()))
                .andExpect(jsonPath("$[0].type").value(transactions.get(0).getType()))
                .andExpect(jsonPath("$[0].amount").value(transactions.get(0).getAmount()))
                .andExpect(jsonPath("$[0].date").value(transactions.get(0).getDate().toString()))
                .andExpect(jsonPath("$[0].details").value(transactions.get(0).getDetails()));
    }

    @Test
    public void testDeleteTransactions() throws Exception {
        mvc.perform(delete("/api/transactions/id?id=4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateTransactions() throws Exception {
        mvc.perform(put("/api/transactions/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Transactions.builder()
                                .id_transaction(1L)
                                .type("asdassad")
                                .amount(122.1)
                                .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .details("Adsadasdsadad1")
                                .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_transaction").value(1L))
                .andExpect(jsonPath("$.type").value("asdassad"))
                .andExpect(jsonPath("$.amount").value(122.1))
                .andExpect(jsonPath("$.date").value("2024-12-31T23:59:59"))
                .andExpect(jsonPath("$.details").value("Adsadasdsadad1"));
    }

    @Test
    public void testTransactionsTypeNull() throws Exception {
        result = mvc.perform(post("/api/transactions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Transactions.builder()
                                .id_transaction(1L)
                                .amount(122.1)
                                .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .details("Adsadasdsadad1")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Type"));
    }

    @Test
    public void testTranslationsAmountNull() throws Exception {
        result = mvc.perform(post("/api/transactions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Transactions.builder()
                                .id_transaction(1L)
                                .type("asdassad")
                                .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .details("Adsadasdsadad1")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Amount"));
    }

    @Test
    public void testTransactionsDateNull() throws Exception {
        result = mvc.perform(post("/api/transactions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Transactions.builder()
                                .id_transaction(1L)
                                .type("asdassad")
                                .amount(122.1)
                                .details("Adsadasdsadad1")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Date"));
    }

    @Test
    public void testTransactionsDetailsNull() throws Exception {
        result = mvc.perform(post("/api/transactions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Transactions.builder()
                                .type("Asdsaa")
                                .id_transaction(1L)
                                .type("asdassad")
                                .amount(122.1)
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Details"));
    }
}