package com.example.DataBase.Primary;

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
public class ProductsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static List<Products> products;

    private MvcResult result;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        products = Arrays.asList(
                Products.builder().id_product(1L).name("Asdads1").description("asdasad1").price(1.21).stock(1999).build(),
                Products.builder().id_product(2L).name("Asdads2").description("asdasad2").price(1.22).stock(2999).build(),
                Products.builder().id_product(3L).name("Asdads3").description("asdasad3").price(1.23).stock(3999).build(),
                Products.builder().id_product(4L).name("Asdads4").description("asdasad4").price(1.24).stock(4999).build());
    }

    @Test
    public void testSaveProducts() throws Exception{
        mvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(products.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_product").value(products.get(0).getId_product().longValue()))
                .andExpect(jsonPath("$.name").value(products.get(0).getName()))
                .andExpect(jsonPath("$.description").value(products.get(0).getDescription()))
                .andExpect(jsonPath("$.price").value(products.get(0).getPrice()))
                .andExpect(jsonPath("$.stock").value(products.get(0).getStock()));

        mvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_product").value(products.get(1).getId_product().longValue()))
                .andExpect(jsonPath("$.name").value(products.get(1).getName()))
                .andExpect(jsonPath("$.description").value(products.get(1).getDescription()))
                .andExpect(jsonPath("$.price").value(products.get(1).getPrice()))
                .andExpect(jsonPath("$.stock").value(products.get(1).getStock()));

        mvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_product").value(products.get(2).getId_product().longValue()))
                .andExpect(jsonPath("$.name").value(products.get(2).getName()))
                .andExpect(jsonPath("$.description").value(products.get(2).getDescription()))
                .andExpect(jsonPath("$.price").value(products.get(2).getPrice()))
                .andExpect(jsonPath("$.stock").value(products.get(2).getStock()));

        mvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products.get(3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_product").value(products.get(3).getId_product().longValue()))
                .andExpect(jsonPath("$.name").value(products.get(3).getName()))
                .andExpect(jsonPath("$.description").value(products.get(3).getDescription()))
                .andExpect(jsonPath("$.price").value(products.get(3).getPrice()))
                .andExpect(jsonPath("$.stock").value(products.get(3).getStock()));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testGetByIdProducts() throws Exception {
        mvc.perform(get("/api/products/id?id=4")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_product").value(products.get(3).getId_product().longValue()))
                .andExpect(jsonPath("$[0].name").value(products.get(3).getName()))
                .andExpect(jsonPath("$[0].description").value(products.get(3).getDescription()))
                .andExpect(jsonPath("$[0].price").value(products.get(3).getPrice()))
                .andExpect(jsonPath("$[0].stock").value(products.get(3).getStock()));
    }

    @Test
    public void testDeleteProducts() throws Exception {
        mvc.perform(delete("/api/products/id?id=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateProducts() throws Exception {
        mvc.perform(put("/api/products/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Products.builder()
                                .id_product(1L)
                                .name("asdasd")
                                .description("asdasdasda")
                                .price(1.212)
                                .stock(11111)
                        .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_product").value(1L))
                .andExpect(jsonPath("$.name").value("asdasd"))
                .andExpect(jsonPath("$.description").value("asdasdasda"))
                .andExpect(jsonPath("$.price").value(1.212))
                .andExpect(jsonPath("$.stock").value(11111));
    }

   @Test
    public void testProductsNameNull() throws Exception {
        result = mvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Products.builder()
                        .description("asdasdasda")
                        .price(1.212)
                        .stock(11111)
                        .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Name"));
   }

    @Test
    public void testProductsDescriptionNull() throws Exception {
        result = mvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Products.builder()
                                .name("asdasdasda")
                                .price(1.212)
                                .stock(11111)
                                .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Description"));
    }

    @Test
    public void testProductsPriceNull() throws Exception {
        result = mvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Products.builder()
                                .description("asdasdasda")
                                .name("1.212")
                                .stock(11111)
                                .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Price"));
    }

    @Test
    public void testProductsStockNull() throws Exception {
        result = mvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Products.builder()
                                .description("asdasdasda")
                                .price(1.212)
                                .name("11111")
                                .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Stock"));
    }
}