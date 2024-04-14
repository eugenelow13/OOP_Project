package com.oop.api.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.api.model.Customer;
import com.oop.api.repository.CustomerRepository;

/**
 * The CustomerService class is responsible for handling operations related to the Customer entity.
 * 
 * This class is a service component in the Spring framework.
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Retrieves all customers from the customer repository.
     *
     * @return an Iterable of Customer objects representing all customers.
     */
    public Iterable<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    /**
     * Retrieves a customer by their email address from the customer repository.
     *
     * @param customer_email the email address of the customer to retrieve.
     * @return an Optional of Customer object representing the customer with the specified email address,
     *         or an empty Optional if no customer is found.
     */
    public Optional<Customer> getCustomerByEmail(String customer_email){
        return customerRepository.findByEmail(customer_email);
    }
}
