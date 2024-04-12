package com.oop.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.oop.api.model.Event;


/**
 * The EventRepository interface is responsible for providing CRUD operations
 * for the Event entity.
 * 
 * This interface extends the CrudRepository interface provided by Spring Data JPA,
 * which allows for basic CRUD operations to be performed on the Event entity.
 * 
 * The EventRepository interface also provides a custom method, findByName,
 * which allows for finding an event by its name.
 * 
 * This interface is annotated with the @Repository annotation, which indicates
 * that it is a repository component in the Spring framework.
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
    /**
     * Finds an event by its name.
     *
     * @param event_name the name of the event to find
     * @return an Optional containing the event if found, or an empty Optional if not found
     */
    public Optional<Event> findByName(String event_name);
}
