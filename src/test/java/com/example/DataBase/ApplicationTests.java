package com.example.DataBase;

import com.example.DataBase.Primary.ClientsTest;
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
	public void ClientsTestAllMethods() throws Exception{
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

}
