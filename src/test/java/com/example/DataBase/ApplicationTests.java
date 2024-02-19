package com.example.DataBase;

import com.example.DataBase.Primary.ClientsTest;
import com.example.DataBase.Primary.OrdersTest;
import com.example.DataBase.Primary.ProductsTest;
import com.example.DataBase.Primary.ReviewsTest;
import com.example.DataBase.Secondary.BudgetsTest;
import com.example.DataBase.Secondary.FinancialReportsTest;
import com.example.DataBase.Secondary.InvoicesTest;
import com.example.DataBase.Secondary.TransactionsTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
class ApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void ClientsTestAllMethods() throws Exception {
        ClientsTest clientsTest = new ClientsTest();
        clientsTest.setMvc(mvc, objectMapper);
        ClientsTest.setUp();

        clientsTest.testSaveClients();
        clientsTest.testGetAllClients();
        clientsTest.testGetByIdClients();
        clientsTest.testDeleteClients();
        clientsTest.testUpdateClients();

        clientsTest.testClientsNameNull();
        clientsTest.testClientsAddressNull();
        clientsTest.testClientsPhoneNumberNull();
    }

    @Test
    public void OrdersTestAllMethods() throws Exception {
        OrdersTest ordersTest = new OrdersTest();
        ordersTest.setMvc(mvc, objectMapper);
        OrdersTest.setUp();

        ordersTest.testSaveOrders();
        ordersTest.testGetAllOrders();
        ordersTest.testGetByIdOrders();
        ordersTest.testDeleteOrders();
        ordersTest.testUpdateOrders();

        ordersTest.testOrdersPriceNull();
        ordersTest.testOrdersTotalPriceNull();
        ordersTest.testOrdersQuantityNull();
    }

    @Test
    public void ProductsTestAllMethods() throws Exception {
        ProductsTest productsTest = new ProductsTest();
        productsTest.setMvc(mvc, objectMapper);
        ProductsTest.setUp();

        productsTest.testSaveProducts();
        productsTest.testGetAllProducts();
        productsTest.testGetByIdProducts();
        productsTest.testDeleteProducts();
        productsTest.testUpdateProducts();

        productsTest.testProductsDescriptionNull();
        productsTest.testProductsNameNull();
        productsTest.testProductsStockNull();
        productsTest.testProductsPriceNull();
    }

    @Test
    public void ReviewsTestAllMethods() throws Exception {
        ReviewsTest reviewsTest = new ReviewsTest();
        reviewsTest.setMvc(mvc, objectMapper);
        ReviewsTest.setUp();

        reviewsTest.testSaveReviews();
        reviewsTest.testGetAllReviews();
        reviewsTest.testGetByIdReviews();
        reviewsTest.testDeleteReviews();
        reviewsTest.testUpdateReviews();

        reviewsTest.testReviewsReviewNull();
    }

    @Test
    public void TransactionsTestAllMethods() throws Exception {
        TransactionsTest transactionsTest = new TransactionsTest();
        transactionsTest.setMvc(mvc, objectMapper);
        TransactionsTest.setUp();

        transactionsTest.testSaveTransactions();
        transactionsTest.testGetAllTransactions();
        transactionsTest.testByIdTransactions();
        transactionsTest.testDeleteTransactions();
        transactionsTest.testUpdateTransactions();

        transactionsTest.testTranslationsAmountNull();
        transactionsTest.testTransactionsDateNull();
        transactionsTest.testTransactionsDetailsNull();
        transactionsTest.testTransactionsTypeNull();
    }

    @Test
    public void InvoicesTestAllMethods() throws Exception {
        InvoicesTest invoicesTest = new InvoicesTest();
        invoicesTest.setMvc(mvc, objectMapper);
        InvoicesTest.setUp();

        invoicesTest.testSaveInvoices();
        invoicesTest.testGetAllInvoices();
        invoicesTest.testByIdInvoices();
        invoicesTest.testDeleteInvoices();
        invoicesTest.testUpdateInvoices();

        invoicesTest.testInvoicesAmountNull();
        invoicesTest.testInvoicesDateNull();
        invoicesTest.testInvoicesNumberNull();
    }

    @Test
    public void FinancialReportsTestAllMethods() throws Exception {
        FinancialReportsTest financialReportsTest = new FinancialReportsTest();
        financialReportsTest.setMvc(mvc, objectMapper);
        FinancialReportsTest.setUp();

        financialReportsTest.testSaveFinancialReports();
        financialReportsTest.testGetAllFinancialReports();
        financialReportsTest.testByIdFinancialReports();
        financialReportsTest.testDeleteFinancialReports();
        financialReportsTest.testUpdateFinancialReports();

        financialReportsTest.testFinancialReportsDateNull();
        financialReportsTest.testFinancialReportsTypeNull();
        financialReportsTest.testFinancialReportsDetailsNull();
    }

    @Test
    public void BudgetsTestAllMethods() throws Exception {
        BudgetsTest budgetsTest = new BudgetsTest();
        budgetsTest.setMvc(mvc, objectMapper);
        BudgetsTest.setUp();

        budgetsTest.testSaveBudgets();
        budgetsTest.testGetAllBudgets();
        budgetsTest.testByIdBudgets();
        budgetsTest.testDeleteBudgets();
        budgetsTest.testUpdateBudgets();

        budgetsTest.testBudgetsAnticipatedNull();
        budgetsTest.testBudgetsAvailableNull();
    }
}
