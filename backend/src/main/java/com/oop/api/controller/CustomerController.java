package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.dto.BookingInfo;
import com.oop.api.model.Customer;
import com.oop.api.service.CustomerService;
import com.oop.api.service.BookingService;


import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookingService bookingService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping(path = "/all")
    @PreAuthorize("hasAnyRole('TICKETING_OFFICER', 'EVENT_MANAGER')")
    public @ResponseBody ResponseEntity<Object> getAllCustomers() {
        Iterable<Customer> customers = customerService.getAllCustomers();
        return generateResponse(customers);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Customer> authenticatedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Customer currentCustomer = (Customer) authentication.getPrincipal();

        return ResponseEntity.ok(currentCustomer);
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
