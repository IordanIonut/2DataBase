package com.example.DataBase.Secondary;

import com.example.DataBase.Secondary.Model.Budgets;
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
public class BudgetsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    private static List<Budgets> budgets;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        budgets = Arrays.asList(
                Budgets.builder().id_budget(4L).available(11.1).anticipated(1.1).build(),
                Budgets.builder().id_budget(2L).available(22.2).anticipated(2.2).build(),
                Budgets.builder().id_budget(3L).available(33.3).anticipated(3.3).build());
    }

    @Test
    public void testSaveBudgets() throws Exception{
        mvc.perform(post("/api/budgets/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(budgets.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_budget").value(budgets.get(0).getId_budget().longValue()))
                .andExpect(jsonPath("$.available").value(budgets.get(0).getAvailable()))
                .andExpect(jsonPath("$.anticipated").value(budgets.get(0).getAnticipated()));

        mvc.perform(post("/api/budgets/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(budgets.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_budget").value(budgets.get(1).getId_budget().longValue()))
                .andExpect(jsonPath("$.available").value(budgets.get(1).getAvailable()))
                .andExpect(jsonPath("$.anticipated").value(budgets.get(1).getAnticipated()));

        mvc.perform(post("/api/budgets/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(budgets.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_budget").value(budgets.get(2).getId_budget().longValue()))
                .andExpect(jsonPath("$.available").value(budgets.get(2).getAvailable()))
                .andExpect(jsonPath("$.anticipated").value(budgets.get(2).getAnticipated()));
    }

    @Test
    public void testGetAllBudgets() throws Exception {
        mvc.perform(get("/api/budgets")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testByIdBudgets() throws Exception{
        mvc.perform(get("/api/budgets/id?id=4")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_budget").value(budgets.get(0).getId_budget()))
                .andExpect(jsonPath("$[0].available").value(budgets.get(0).getAvailable()))
                .andExpect(jsonPath("$[0].anticipated").value(budgets.get(0).getAnticipated()));
    }

    @Test
    public void testDeleteBudgets() throws Exception{
        mvc.perform(delete("/api/budgets/id?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateBudgets()throws Exception{
        mvc.perform(put("/api/budgets/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Budgets.builder()
                        .id_budget(2L)
                        .available(222.2)
                        .anticipated(222.2)
                        .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_budget").value(2L))
                .andExpect(jsonPath("$.anticipated").value(222.2))
                .andExpect(jsonPath("$.available").value(222.2));
    }

    @Test
    public void testBudgetsAvailableNull() throws Exception{
        result = mvc.perform(post("/api/budgets/add")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Budgets.builder()
                                .id_budget(2L)
                                .anticipated(22.2)
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Avaibable"));
    }

    @Test
    public void testBudgetsAnticipatedNull() throws Exception{
        result = mvc.perform(post("/api/budgets/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Budgets.builder()
                                .id_budget(2L)
                                .available(222.2)
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Avaibable"));
    }
}
