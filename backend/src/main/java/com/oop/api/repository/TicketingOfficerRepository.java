package com.oop.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.oop.api.model.TicketingOfficer;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface TicketingOfficerRepository extends CrudRepository<TicketingOfficer, Integer> {
    public Optional<TicketingOfficer> findByEmail(String ticketing_officer_email);

    
}