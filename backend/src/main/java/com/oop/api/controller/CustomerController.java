package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.model.Customer;
import com.oop.api.service.CustomerService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<Object> getAllCustomers() {
        Iterable<Customer> customers = customerService.getAllCustomers();
        return generateResponse(customers);
    }

    @GetMapping(path = "/{username}")
    public @ResponseBody Optional<Customer> getCustomer(@PathVariable String email) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);

        if (customer.isEmpty())
            throw new EntityNotFoundException("Customer not found");

        return customer;
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> addNewCustomer(@Valid @RequestBody Customer customer) {
        customerService.addNewCustomer(customer);


        return generateResponse("Account is successfully created", (Object) customer);
    }    
    
    
}
