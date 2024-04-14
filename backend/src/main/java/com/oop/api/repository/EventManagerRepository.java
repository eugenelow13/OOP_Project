package com.oop.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.oop.api.model.EventManager;

/**
 * The EventManagerRepository interface is responsible for providing CRUD operations
 * for the EventManager entity.
 * 
 * This interface extends the CrudRepository interface provided by Spring Data JPA,
 * which allows for basic CRUD operations to be performed on the EventManager entity.
 * 
 * The EventManagerRepository interface also provides a custom method, findByEmail,
 * which allows for finding an event manager by their email address.
 * 
 * This interface is annotated with the @Repository annotation, which indicates
 * that it is a repository component in the Spring framework.
 */
@Repository
public interface EventManagerRepository extends CrudRepository<EventManager, Integer> {
    public Optional<EventManager> findByEmail(String event_manager_email);

    
}