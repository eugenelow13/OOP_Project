package com.oop.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.oop.api.model.EventManager;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface EventManagerRepository extends CrudRepository<EventManager, Integer> {
    public Optional<EventManager> findByEmail(String event_manager_email);

    
}
