package com.oop.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oop.api.model.Ticket;


/**
 * The TicketRepository interface is responsible for providing CRUD operations
 * for the Ticket entity.
 * 
 * This interface extends the CrudRepository interface provided by Spring Data JPA,
 * which allows for basic CRUD operations to be performed on the Ticket entity.
 * 
 * The TicketRepository interface also provides custom methods specific to the Ticket entity.
 * 
 * This interface is annotated with the @Repository annotation, which indicates
 * that it is a repository component in the Spring framework.
 */
@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

}
