package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public @ResponseBody Optional<Customer> getCustomer(@RequestParam String email) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);

        if (customer.isEmpty())
            throw new EntityNotFoundException("Customer not found");

        return customer;
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> addNewCustomer(@Valid @RequestBody Customer customer) {

        try {
            customerService.addNewCustomer(customer);
        } catch (DataIntegrityViolationException e) {
            return generateResponse("Account already exists. Please use a different email.", (Object) customer);
        }

        return generateResponse("Account is successfully created", (Object) customer);
    }

    @PostMapping(path = "/updateCreditBalance")
    public ResponseEntity<Object> updateCreditBalance(@Valid @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateCreditBalance(updatedCustomer, updatedCustomer.getCreditBalance());
        return generateResponse("Credit Balance is successfully updated", (Object) customer);
    }
    @PostMapping(path = "/updateBookingHistory")
    public ResponseEntity<Object> updateBookingHistory(@Valid @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateBookingHistory(updatedCustomer, updatedCustomer.getBookingHistory());
        return generateResponse("Booking History is successfully updated", (Object) customer);
    }
    
}
