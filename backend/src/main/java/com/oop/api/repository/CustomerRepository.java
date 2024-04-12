package com.oop.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.oop.api.model.Customer;

/**
 * The CustomerRepository interface is responsible for providing CRUD operations
 * for the Customer entity.
 * 
 * This interface extends the CrudRepository interface provided by Spring Data JPA,
 * which allows for basic CRUD operations to be performed on the Customer entity.
 * 
 * The CustomerRepository interface also provides a custom method, findByEmail,
 * which allows for finding a customer by their email address.
 * 
 * This interface is annotated with the @Repository annotation, which indicates
 * that it is a repository component in the Spring framework.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    /**
     * Finds a customer by their email address.
     * 
     * @param customer_email the email address of the customer to find
     * @return an Optional containing the customer if found, or an empty Optional if not found
     */
    public Optional<Customer> findByEmail(String customer_email);
}
