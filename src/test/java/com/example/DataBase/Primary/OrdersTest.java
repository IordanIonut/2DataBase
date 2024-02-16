package com.example.DataBase.Primary;

import com.example.DataBase.Primary.Model.Clients;
import com.example.DataBase.Primary.Model.Orders;
import com.example.DataBase.Primary.Model.Products;
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
public class OrdersTest {
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    private static List<Clients> clients;

    private static List<Orders> orders;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }
    
    @BeforeAll
    public static void setUp() throws Exception {
        ClientsTest.setUp();
        clients = ClientsTest.clients;

        orders = Arrays.asList(
                Orders.builder().id_order(1L).id_client(clients.get(0)).price(12.1).quantity(1).total_price(1.0).build(),
                Orders.builder().id_order(2L).id_client(clients.get(1)).price(12.2).quantity(2).total_price(2.0).build(),
                Orders.builder().id_order(3L).id_client(clients.get(2)).price(12.3).quantity(3).total_price(3.0).build(),
                Orders.builder().id_order(4L).id_client(clients.get(3)).price(12.4).quantity(4).total_price(4.0).build());
    }

    @Test
    public void testSaveOrders() throws Exception {
        mvc.perform(post("/api/orders/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orders.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_order").value(orders.get(0).getId_order().longValue()))
                .andExpect(jsonPath("$.id_client.id_client").value(orders.get(0).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$.price").value(orders.get(0).getPrice()))
                .andExpect(jsonPath("$.quantity").value(orders.get(0).getQuantity()))
                .andExpect(jsonPath("$.total_price").value(orders.get(0).getTotal_price()));

        mvc.perform(post("/api/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orders.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_order").value(orders.get(1).getId_order().longValue()))
                .andExpect(jsonPath("$.id_client.id_client").value(orders.get(1).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$.price").value(orders.get(1).getPrice()))
                .andExpect(jsonPath("$.quantity").value(orders.get(1).getQuantity()))
                .andExpect(jsonPath("$.total_price").value(orders.get(1).getTotal_price()));

        mvc.perform(post("/api/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orders.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_order").value(orders.get(2).getId_order().longValue()))
                .andExpect(jsonPath("$.id_client.id_client").value(orders.get(2).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$.price").value(orders.get(2).getPrice()))
                .andExpect(jsonPath("$.quantity").value(orders.get(2).getQuantity()))
                .andExpect(jsonPath("$.total_price").value(orders.get(2).getTotal_price()));

        mvc.perform(post("/api/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orders.get(3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_order").value(orders.get(3).getId_order().longValue()))
                .andExpect(jsonPath("$.id_client.id_client").value(orders.get(3).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$.price").value(orders.get(3).getPrice()))
                .andExpect(jsonPath("$.quantity").value(orders.get(3).getQuantity()))
                .andExpect(jsonPath("$.total_price").value(orders.get(3).getTotal_price()));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testGetByIdOrders() throws Exception {
        mvc.perform(get("/api/orders/id?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_order").value(orders.get(0).getId_order().longValue()))
                .andExpect(jsonPath("$[0].id_client.id_client").value(orders.get(0).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$[0].price").value(orders.get(0).getPrice()))
                .andExpect(jsonPath("$[0].quantity").value(orders.get(0).getQuantity()))
                .andExpect(jsonPath("$[0].total_price").value(orders.get(0).getTotal_price()));
    }

    @Test
    public void testDeleteOrders() throws Exception {
        mvc.perform(delete("/api/orders/id?id=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateOrders() throws Exception {
        mvc.perform(put("/api/orders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Orders.builder()
                                .id_order(1L)
                                .id_client(clients.get(0))
                                .price(111.1)
                                .quantity(111)
                                .total_price(111111.0)
                        .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_order").value(1L))
                .andExpect(jsonPath("$.id_client.id_client").value(clients.get(0).getId_client().longValue()))
                .andExpect(jsonPath("$.price").value(111.1))
                .andExpect(jsonPath("$.quantity").value(111))
                .andExpect(jsonPath("$.total_price").value(111111.0));
    }

    @Test
    public void testOrdersPriceNull() throws Exception{
        result = mvc.perform(post("/api/orders/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Orders.builder()
                        .id_client(clients.get(0))
                        .quantity(111)
                        .total_price(111111.0)
                        .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Price"));
    }

    @Test
    public void testOrdersQuantityNull() throws Exception{
        result = mvc.perform(post("/api/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Orders.builder()
                                .id_client(clients.get(0))
                                .price(111.0)
                                .total_price(111111.0)
                                .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Quantity"));
    }

    @Test
    public void testOrdersTotalPriceNull() throws Exception{
        result = mvc.perform(post("/api/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Orders.builder()
                                .id_client(clients.get(0))
                                .quantity(111)
                                .quantity(111111)
                                .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Total Price"));
    }
}