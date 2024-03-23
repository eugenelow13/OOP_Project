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

@Service
public class EventManagerService {

    @Autowired
    private EventManagerRepository eventManagerRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public Iterable<EventManager> getAllEventManagers(){
        return eventManagerRepository.findAll();
    }

    public Optional<EventManager> getEventManagerByEmail(String event_manager_email){
        return eventManagerRepository.findByEmail(event_manager_email);
    }
    
}