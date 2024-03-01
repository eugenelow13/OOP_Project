package com.oop.api.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.api.model.EventManager;
import com.oop.api.repository.EventManagerRepository;

@Service
public class EventManagerService {
    @Autowired
    private EventManagerRepository eventManagerRepository;

    public Iterable<EventManager> getAllEventManagers(){
        return eventManagerRepository.findAll();
    }

    public Optional<EventManager> getEventManagerByEmail(String event_manager_email){
        return eventManagerRepository.findByEmail(event_manager_email);
    }


    public void addNewEventManager(EventManager eventManager){
        eventManagerRepository.save(eventManager);
    }
    
}
