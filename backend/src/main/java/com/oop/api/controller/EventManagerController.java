package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.model.EventManager;
import com.oop.api.service.EventManagerService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/event_managers")
public class EventManagerController {

    @Autowired
    private EventManagerService eventManagerService;

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<Object> getAllEventManagers() {
        Iterable<EventManager> eventManagers = eventManagerService.getAllEventManagers();
        return generateResponse(eventManagers);
    }

    @GetMapping(path = "/{username}")
    public @ResponseBody Optional<EventManager> getEventManager(@RequestParam String email) {
        Optional<EventManager> eventManager = eventManagerService.getEventManagerByEmail(email);

        if (eventManager.isEmpty())
            throw new EntityNotFoundException("Event Manager not found");

        return eventManager;
    }
    
    
}