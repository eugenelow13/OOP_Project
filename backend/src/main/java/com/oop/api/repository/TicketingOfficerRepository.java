package com.oop.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.oop.api.model.TicketingOfficer;

/**
 * The TicketingOfficerRepository interface is responsible for providing CRUD operations
 * for the TicketingOfficer entity.
 * 
 * This interface extends the CrudRepository interface provided by Spring Data JPA,
 * which allows for basic CRUD operations to be performed on the TicketingOfficer entity.
 * 
 * The TicketingOfficerRepository interface also provides a custom method, findByEmail,
 * which allows for finding a ticketing officer by their email address.
 * 
 * This interface is annotated with the @Repository annotation, which indicates
 * that it is a repository component in the Spring framework.
 */
@Repository
public interface TicketingOfficerRepository extends CrudRepository<TicketingOfficer, Integer> {
    /**
     * Finds a TicketingOfficer by their email.
     *
     * @param ticketing_officer_email the email of the TicketingOfficer to find
     * @return an Optional containing the TicketingOfficer if found, or an empty Optional if not found
     */
    public Optional<TicketingOfficer> findByEmail(String ticketing_officer_email);
}