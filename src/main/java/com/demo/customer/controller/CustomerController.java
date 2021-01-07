package com.demo.customer.controller;


import com.demo.customer.model.Customer;
import com.demo.customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/customers")
    public String postCustomer(@RequestBody Customer customer) throws JsonProcessingException {
        customer.setId("712e2132-affa-4bb6-8d17-b13c16b2c9b3");
        customerList.add(customer);

        for(Customer newCustomer:customerList){
            if(newCustomer.getId().equalsIgnoreCase(customer.getId())){
                return mapper.writeValueAsString(newCustomer);
            }
        }
        return null;
    }
}
