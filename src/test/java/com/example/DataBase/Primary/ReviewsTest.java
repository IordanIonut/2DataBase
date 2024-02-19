package com.example.DataBase.Primary;

import com.example.DataBase.Primary.Model.Clients;
import com.example.DataBase.Primary.Model.Products;
import com.example.DataBase.Primary.Model.Reviews;
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
public class ReviewsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Reviews> reviews;

    public static List<Products> products;

    public static List<Clients> clients;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        ProductsTest.setUp();
        products = ProductsTest.products;
        ClientsTest.setUp();
        clients = ClientsTest.clients;

        reviews = Arrays.asList(
                Reviews.builder().id_review(1L).id_product(products.get(0)).id_client(clients.get(0)).review("Asdasdas1").build(),
                Reviews.builder().id_review(2L).id_product(products.get(1)).id_client(clients.get(1)).review("Asdasdas2").build(),
                Reviews.builder().id_review(3L).id_product(products.get(2)).id_client(clients.get(2)).review("Asdasdas3").build(),
                Reviews.builder().id_review(4L).id_product(products.get(3)).id_client(clients.get(3)).review("Asdasdas4").build());
    }

    @Test
    public void testSaveReviews() throws Exception {
        mvc.perform(post("/api/reviews/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviews.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_review").value(reviews.get(0).getId_review().longValue()))
                .andExpect(jsonPath("$.id_product.id_product").value(reviews.get(0).getId_product().getId_product().longValue()))
                .andExpect(jsonPath("$.id_client.id_client").value(reviews.get(0).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$.review").value(reviews.get(0).getReview()));

        mvc.perform(post("/api/reviews/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviews.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_review").value(reviews.get(1).getId_review().longValue()))
                .andExpect(jsonPath("$.id_product.id_product").value(reviews.get(1).getId_product().getId_product().longValue()))
                .andExpect(jsonPath("$.id_client.id_client").value(reviews.get(1).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$.review").value(reviews.get(1).getReview()));

        mvc.perform(post("/api/reviews/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviews.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_review").value(reviews.get(2).getId_review().longValue()))
                .andExpect(jsonPath("$.id_product.id_product").value(reviews.get(2).getId_product().getId_product().longValue()))
                .andExpect(jsonPath("$.id_client.id_client").value(reviews.get(2).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$.review").value(reviews.get(2).getReview()));

        mvc.perform(post("/api/reviews/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviews.get(3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_review").value(reviews.get(3).getId_review().longValue()))
                .andExpect(jsonPath("$.id_product.id_product").value(reviews.get(3).getId_product().getId_product().longValue()))
                .andExpect(jsonPath("$.id_client.id_client").value(reviews.get(3).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$.review").value(reviews.get(3).getReview()));
    }

    @Test
    public void testGetAllReviews() throws Exception {
        mvc.perform(get("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testGetByIdReviews() throws Exception {
        mvc.perform(get("/api/reviews/id?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_review").value(reviews.get(0).getId_review().longValue()))
                .andExpect(jsonPath("$[0].id_product.id_product").value(reviews.get(0).getId_product().getId_product().longValue()))
                .andExpect(jsonPath("$[0].id_client.id_client").value(reviews.get(0).getId_client().getId_client().longValue()))
                .andExpect(jsonPath("$[0].review").value(reviews.get(0).getReview()));
    }

    @Test
    public void testDeleteReviews() throws Exception{
        mvc.perform(delete("/api/reviews/id?id=6")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateReviews() throws Exception{
        mvc.perform(put("/api/reviews/{id}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Reviews.builder()
                                .id_review(2L)
                                .id_product(products.get(0))
                                .id_client(clients.get(0))
                                .review("aasddddddddddddddddddddddddddddd")
                        .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_review").value(2L))
                .andExpect(jsonPath("$.id_product.id_product").value(products.get(0).getId_product().longValue()))
                .andExpect(jsonPath("$.id_client.id_client").value(clients.get(0).getId_client().longValue()))
                .andExpect(jsonPath("$.review").value("aasddddddddddddddddddddddddddddd"));
    }

    @Test
    public void testReviewsReviewNull() throws Exception {
        result = mvc.perform(post("/api/reviews/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Reviews.builder()
                        .id_review(1L)
                        .id_product(products.get(0))
                        .id_client(clients.get(0))
                        .build())))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Review"));
    }
}
