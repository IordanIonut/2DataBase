package com.example.DataBase.Secondary;

import com.example.DataBase.Secondary.Model.FinancialReports;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class FinancialReportsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<FinancialReports> financialReports;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        financialReports = Arrays.asList(
                FinancialReports.builder().id_report(1L).type("asdas1").date(LocalDateTime.parse("2024-12-31T23:59:59")).details("asdasas1").build(),
                FinancialReports.builder().id_report(2L).type("asdas2").date(LocalDateTime.parse("2024-12-31T23:59:59")).details("asdasas2").build(),
                FinancialReports.builder().id_report(3L).type("asdas3").date(LocalDateTime.parse("2024-12-31T23:59:59")).details("asdasas3").build());
    }

    @Test
    public void testSaveFinancialReports() throws Exception {
        mvc.perform(post("/api/financial/reports/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(financialReports.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_report").value(financialReports.get(0).getId_report().longValue()))
                .andExpect(jsonPath("$.type").value(financialReports.get(0).getType()))
                .andExpect(jsonPath("$.date").value(financialReports.get(0).getDate().toString()))
                .andExpect(jsonPath("$.details").value(financialReports.get(0).getDetails()));

        mvc.perform(post("/api/financial/reports/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(financialReports.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_report").value(financialReports.get(1).getId_report().longValue()))
                .andExpect(jsonPath("$.type").value(financialReports.get(1).getType()))
                .andExpect(jsonPath("$.date").value(financialReports.get(1).getDate().toString()))
                .andExpect(jsonPath("$.details").value(financialReports.get(1).getDetails()));

        mvc.perform(post("/api/financial/reports/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(financialReports.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_report").value(financialReports.get(2).getId_report().longValue()))
                .andExpect(jsonPath("$.type").value(financialReports.get(2).getType()))
                .andExpect(jsonPath("$.date").value(financialReports.get(2).getDate().toString()))
                .andExpect(jsonPath("$.details").value(financialReports.get(2).getDetails()));
    }

    @Test
    public void testGetAllFinancialReports() throws Exception {
        mvc.perform(get("/api/financial/reports")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testByIdFinancialReports() throws Exception {
        mvc.perform(get("/api/financial/reports/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_report").value(financialReports.get(0).getId_report().longValue()))
                .andExpect(jsonPath("$[0].type").value(financialReports.get(0).getType()))
                .andExpect(jsonPath("$[0].date").value(financialReports.get(0).getDate().toString()))
                .andExpect(jsonPath("$[0].details").value(financialReports.get(0).getDetails()));
    }

    @Test
    public void testDeleteFinancialReports() throws Exception{
        mvc.perform(delete("/api/financial/reports/id?id=4")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateFinancialReports() throws Exception{
        mvc.perform(put("/api/financial/reports/{id}",2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(FinancialReports.builder()
                                .id_report(2L)
                                .type("asdas222")
                                .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .details("asdasas222")
                                .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_report").value(2L))
                .andExpect(jsonPath("$.type").value("asdas222"))
                .andExpect(jsonPath("$.date").value("2024-12-31T23:59:59"))
                .andExpect(jsonPath("$.details").value("asdasas222"));
    }

    @Test
    public void testFinancialReportsTypeNull() throws Exception {
        result = mvc.perform(post("/api/financial/reports/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(FinancialReports.builder()
                        .id_report(2L)
                        .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                        .details("asdasas222")
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Type"));
    }

    @Test
    public void testFinancialReportsDateNull() throws Exception {
        result = mvc.perform(post("/api/financial/reports/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FinancialReports.builder()
                                .id_report(2L)
                                .type("asdas222")
                                .details("asdasas222")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Date"));
    }

    @Test
    public void testFinancialReportsDetailsNull() throws Exception {
        result = mvc.perform(post("/api/financial/reports/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FinancialReports.builder()
                                .id_report(2L)
                                .type("asdas222")
                                .date(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Details"));
    }
}
