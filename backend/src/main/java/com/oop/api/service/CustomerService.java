package com.oop.api.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.api.model.Customer;
import com.oop.api.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Iterable<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerByEmail(String customer_email){
        return customerRepository.findByEmail(customer_email);
    }

    // public void addNewCustomer(Customer customer){
    //     customerRepository.save(customer);
    // }

    // get creditBalance of customer
    
    // update creditBalance of customer

    // delete customer

}
