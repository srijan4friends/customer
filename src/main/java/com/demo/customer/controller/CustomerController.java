package com.demo.customer.controller;


import com.demo.customer.model.Customer;
import com.demo.customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    List<Customer> customerList;


    public CustomerService customerService;
    ObjectMapper mapper;

    @Autowired
    public CustomerController(CustomerService customerService) throws IOException {
        mapper = new ObjectMapper();
        this.customerList = customerService.loadCustomer();
    }

    public CustomerController() {
    }

    @GetMapping("/api/customers/{id}")
    public String getSingleCustomer(@PathVariable String id) throws IOException {

        for(Customer customer:customerList){
            if(customer.getId().equalsIgnoreCase(id)){
                mapper=new ObjectMapper();
                return mapper.writeValueAsString(customer);
            }
        }
        return null;
    }

    @GetMapping("/api/customers")
    public String getAllCustomers() throws JsonProcessingException {
        return mapper.writeValueAsString(customerList);
    }
}
