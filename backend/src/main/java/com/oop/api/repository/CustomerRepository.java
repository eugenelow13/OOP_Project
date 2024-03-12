package com.oop.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.oop.api.model.Customer;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    public Optional<Customer> findByEmail(String customer_email);

}
