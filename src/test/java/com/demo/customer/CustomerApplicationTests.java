package com.demo.customer;

import com.demo.customer.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
class CustomerApplicationTests {

	@Autowired
	private MockMvc mvc;
	ObjectMapper mapper;ArrayList<Customer> customerList;

	String customersJsonPath = "src/test/data/customers.json"; // 4 customers
	String customerJsonPath = "src/test/data/existingCustomer.json"; // 1 customer
	String newCustomerJsonPath = "src/test/data/newCustomer.json"; // 1 customer


	@BeforeEach
	void setUp() throws IOException {
		mapper = new ObjectMapper();
		initializeCustomersData();
	}

	// TEST UTILITIES ----------------------------------------------------


	@Test
	void contextLoads() {
	}

	private void initializeCustomersData() throws IOException {
		mapper = new ObjectMapper();
		File customersFile = new File(customersJsonPath);
		customerList = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {});
	}

	private String getCustomerJsonString() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File customerFile = new File(customerJsonPath);
		Customer customer = mapper.readValue(customerFile, Customer.class);
		return mapper.writeValueAsString(customer);
	}

	private String createCustomerJsonString() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File customerFile = new File(newCustomerJsonPath);
		Customer customer = mapper.readValue(customerFile, Customer.class);
		return mapper.writeValueAsString(customer);
	}

	@Test
	public void getSingleCustomerTest() throws Exception {
		mvc.perform(get("/api/customers/b8a504e8-7cbd-4a54-9a24-dc1832558162"))
				.andExpect(status().isOk())
				.andExpect(content().json(getCustomerJsonString()));
	}

	@Test
	public void getAllCustomerTest() throws Exception {
		mvc.perform(get("/api/customers"))
			.andExpect((status().isOk()))
				.andExpect(content().json(mapper.writeValueAsString(customerList)));
	}
	@Test
	public void postCustomerTest() throws Exception {
		String customerJsonStr = "{\n" +
				"  \"firstName\": \"Araminta\",\n" +
				"  \"lastName\": \"Ross\",\n" +
				"  \"address\": \"1849 Harriet Ave, Auburn, NY 63102\",\n" +
				"  \"phoneNumber\": \"309-555-1370\"\n" +
				"}";
		mvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
						.content(customerJsonStr))
				.andExpect(status().isOk())
				.andExpect(content().json(customerJsonStr.replace("\"id\":null","\"id\":\"712e2132-affa-4bb6-8d17-b13c16b2c9b3\"")));
	}

}
