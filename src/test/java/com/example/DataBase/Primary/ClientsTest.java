package com.example.DataBase.Primary;

import com.example.DataBase.Primary.Model.Clients;
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
public class ClientsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static List<Clients> clients;

    private MvcResult result;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        clients = Arrays.asList(
                Clients.builder().id_client(1L).name("asdasdas").address("Asdasdadas").phone_number(12313131).build(),
                Clients.builder().id_client(2L).name("asdasdas2").address("Asdasdadas111").phone_number(456463453).build(),
                Clients.builder().id_client(3L).name("asdasdas12").address("Asdasdadas2222").phone_number(768768678).build(),
                Clients.builder().id_client(4L).name("asdasdas12").address("Asdasdadas2222").phone_number(768768678).build());
    }

    @Test
    public void testSaveClients() throws Exception {
        mvc.perform(post("/api/clients/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clients.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_client").value(clients.get(0).getId_client().longValue()))
                .andExpect(jsonPath("$.name").value(clients.get(0).getName()))
                .andExpect(jsonPath("$.address").value(clients.get(0).getAddress()))
                .andExpect(jsonPath("$.phone_number").value(clients.get(0).getPhone_number()));

        mvc.perform(post("/api/clients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clients.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_client").value(clients.get(1).getId_client().longValue()))
                .andExpect(jsonPath("$.name").value(clients.get(1).getName()))
                .andExpect(jsonPath("$.address").value(clients.get(1).getAddress()))
                .andExpect(jsonPath("$.phone_number").value(clients.get(1).getPhone_number()));

        mvc.perform(post("/api/clients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clients.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_client").value(clients.get(2).getId_client().longValue()))
                .andExpect(jsonPath("$.name").value(clients.get(2).getName()))
                .andExpect(jsonPath("$.address").value(clients.get(2).getAddress()))
                .andExpect(jsonPath("$.phone_number").value(clients.get(2).getPhone_number()));

        mvc.perform(post("/api/clients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clients.get(3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_client").value(clients.get(3).getId_client().longValue()))
                .andExpect(jsonPath("$.name").value(clients.get(3).getName()))
                .andExpect(jsonPath("$.address").value(clients.get(3).getAddress()))
                .andExpect(jsonPath("$.phone_number").value(clients.get(3).getPhone_number()));
    }

    @Test
    public void testGetAllClients() throws Exception {
        mvc.perform(get("/api/clients")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testGetByIdClients() throws Exception {
        mvc.perform(get("/api/clients/id?id=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_client").value(clients.get(2).getId_client().longValue()))
                .andExpect(jsonPath("$[0].name").value(clients.get(2).getName()))
                .andExpect(jsonPath("$[0].address").value(clients.get(2).getAddress()))
                .andExpect(jsonPath("$[0].phone_number").value(clients.get(2).getPhone_number()));
    }

    @Test
    public void testDeleteClients() throws Exception {
        mvc.perform(delete("/api/clients/id?id=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateClients() throws Exception {
        mvc.perform(put("/api/clients/{id}",2L)
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Clients.builder()
                                .id_client(2L)
                                .name("123121212")
                                .address("asdasdsad")
                                .phone_number(1231231212)
                        .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_client").value(2L))
                .andExpect(jsonPath("$.name").value("123121212"))
                .andExpect(jsonPath("$.address").value("asdasdsad"))
                .andExpect(jsonPath("$.phone_number").value(1231231212));
    }

    @Test
    public void testClientsNameNull() throws Exception {
        result = mvc.perform(post("/api/clients/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Clients.builder()
                        .address("asdasdsad")
                        .phone_number(1231231212)
                        .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Name"));
    }


    @Test
    public void testClientsAddressNull() throws Exception {
        result = mvc.perform(post("/api/clients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Clients.builder()
                                .name("asdasdsad")
                                .phone_number(1231231212)
                                .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Address"));
    }


    @Test
    public void testClientsPhoneNumberNull() throws Exception {
        result = mvc.perform(post("/api/clients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Clients.builder()
                                .address("asdasdsad")
                                .name("1231231212")
                                .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Name"));
    }
}
