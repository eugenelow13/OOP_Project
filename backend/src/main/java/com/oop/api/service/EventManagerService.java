package com.oop.api.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oop.api.dto.RegisterUserDTO;
import com.oop.api.model.RoleEnum;
import com.oop.api.model.Role;
import com.oop.api.repository.RoleRepository;
import com.oop.api.model.EventManager;
import com.oop.api.repository.EventManagerRepository;

/**
 * The EventManagerService class provides methods to manage event managers.
 */
@Service
public class EventManagerService {
    @Autowired
    private EventManagerRepository eventManagerRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    /**
     * Constructs a new EventManagerService with the specified dependencies.
     *
     * @param eventManagerRepository The repository for managing event manager data.
     * @param roleRepository The repository for managing role data.
     * @param passwordEncoder The password encoder for encoding and decoding passwords.
     */
    public EventManagerService(EventManagerRepository eventManagerRepository, 
                                RoleRepository roleRepository, 
                                PasswordEncoder passwordEncoder) {
        this.eventManagerRepository = eventManagerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves all event managers from the repository.
     *
     * @return an Iterable of EventManager objects representing all event managers.
     */
    public Iterable<EventManager> getAllEventManagers(){
        return eventManagerRepository.findAll();
    }

    /**
     * Retrieves an EventManager object based on the provided email.
     *
     * @param event_manager_email The email of the EventManager to retrieve.
     * @return An Optional containing the EventManager object if found, or an empty Optional if not found.
     */
    public Optional<EventManager> getEventManagerByEmail(String event_manager_email){
        return eventManagerRepository.findByEmail(event_manager_email);
    }

    /**
     * Creates a new EventManager based on the provided input.
     * @param input The input data for creating the EventManager.
     * @return The created EventManager object.
     */
    public EventManager createEventManager(RegisterUserDTO input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.EVENT_MANAGER);
            
        if (optionalRole.isEmpty()) {
            return null;
        }
            
        var eventManager = new EventManager();
        eventManager.setFullName(input.getFullName());
        eventManager.setEmail(input.getEmail());
        eventManager.setPassword(passwordEncoder.encode(input.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(optionalRole.get());
        eventManager.setRoles(roles);

        return eventManagerRepository.save(eventManager);
    }
}