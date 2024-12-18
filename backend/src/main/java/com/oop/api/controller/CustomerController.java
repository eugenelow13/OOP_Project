package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

/**
 * The CustomerController class handles HTTP requests related to customers.
 */

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

    @GetMapping(path = "")
    public @ResponseBody Optional<Customer> getCustomer(@RequestParam String email) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);

        if (customer.isEmpty())
            throw new EntityNotFoundException("Customer not found");

        return customer;
    }

    @GetMapping("/bookings")
    public ResponseEntity<Object> getCustomerBookings(@RequestParam String email) {
        List<BookingInfo> bookings = bookingService.getAllBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    
}
