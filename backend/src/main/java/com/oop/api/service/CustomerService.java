package com.oop.api.service;
import com.oop.api.model.Booking;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public void addNewCustomer(Customer customer){
        try {
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Customer already exists");
        }
    }

     // get creditBalance of customer
    
     // update creditBalance of customer
     public Customer updateCreditBalance(Customer customer, double newCreditBalance){
        Optional <Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if(optionalCustomer.isPresent()){
            customer = optionalCustomer.get();
            customer.setCreditBalance(newCreditBalance);
            return customerRepository.save(customer);
        }
        else
        {
            throw new EntityNotFoundException("Customer not found");
        }
    
        // update bookingHistory of customer
      
       

     // delete customer
    }

    public Customer updateBookingHistory(Customer customer, List<Booking> newBookingHistory)
    {
        Optional <Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if(optionalCustomer.isPresent()){
            customer = optionalCustomer.get();
            customer.setBookingHistory(newBookingHistory);
            return customerRepository.save(customer);
        }
        else
        {
            throw new EntityNotFoundException("Customer not found");
        }
    }

}
