package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.oop.api.model.EventManager;
import com.oop.api.service.EventManagerService;

import com.oop.api.dto.RegisterUserDTO;
import jakarta.persistence.EntityNotFoundException;

/**
 * The EventManagerController class is responsible for handling HTTP requests related to event managers.
 * It provides endpoints for retrieving event managers, creating event managers, and retrieving the currently authenticated event manager.
 */

@RestController
@RequestMapping("/event_managers")
public class EventManagerController {

    @Autowired
    private EventManagerService eventManagerService;

    @GetMapping(path = "/all")
    public @ResponseBody ResponseEntity<Object> getAllEventManagers() {
        Iterable<EventManager> eventManagers = eventManagerService.getAllEventManagers();
        return generateResponse(eventManagers);
    }

    @GetMapping(path = "")
    public @ResponseBody Optional<EventManager> getEventManager(@RequestParam String email) {
        Optional<EventManager> eventManager = eventManagerService.getEventManagerByEmail(email);

        if (eventManager.isEmpty())
            throw new EntityNotFoundException("Event Manager not found");

        return eventManager;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventManager> authenticatedEventManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        EventManager currentEventManager = (EventManager) authentication.getPrincipal();

        return ResponseEntity.ok(currentEventManager);
    }

    @PostMapping("/create_event_managers")
    public ResponseEntity<EventManager> registerEventManager(@RequestBody RegisterUserDTO registerUserDto) {
        EventManager registeredEventManager = eventManagerService.createEventManager(registerUserDto);

        return ResponseEntity.ok(registeredEventManager);
    }
    
}