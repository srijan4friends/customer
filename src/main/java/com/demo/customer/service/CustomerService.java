package com.demo.customer.service;

import com.demo.customer.model.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    ObjectMapper mapper;
    ArrayList<Customer> customerList;

    String customersJsonPath = "src/test/data/customers.json"; // 4 customers
    String customerJsonPath = "src/test/data/existingCustomer.json"; // 1 customer
    String newCustomerJsonPath = "src/test/data/newCustomer.json"; // 1 customer

    public List loadCustomer() throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        customerList = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {});
        return customerList;
    }
}
