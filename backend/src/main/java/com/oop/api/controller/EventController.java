package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.email.EmailService;
import com.oop.api.model.Event;
import com.oop.api.service.EventService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    private HashSet<String> patchableFields = new HashSet<>(Arrays.asList("name", "type", "venue", "date", "ticketPrice", "cancellationFee", "ticketsAvailable", "customerAttendance", "eventStatus"));

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<Object> getAllEvents() {
        Iterable<Event> events = eventService.getAllEvents();
        return generateResponse(events);
    }

    @GetMapping(path = "/{eventId}")
    public @ResponseBody ResponseEntity<Object> getEvent(@RequestParam Integer eventId) {
        Optional<Event> event = eventService.getEventById(eventId);

        if (event.isEmpty())
            throw new EntityNotFoundException("Event not found");

        return generateResponse(event.get());
    }

    @PostMapping(path = "")
    @PreAuthorize("hasRole('EVENT_MANAGER')")
    public ResponseEntity<Object> addNewEvent(@Valid @RequestBody Event event) {
        eventService.addNewEvent(event);
        return generateResponse("Event is successfully created", (Object) event, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{eventId}")
    @PreAuthorize("hasRole('EVENT_MANAGER')")
    public ResponseEntity<Object> updateEvent(@RequestParam Integer eventId, @Valid @RequestBody Event updatedEventWithoutId) {
        updatedEventWithoutId.setId(eventId);
        Event updatedEvent = updatedEventWithoutId;

        Event result = eventService.updateEvent(updatedEvent);
        return generateResponse("Event is successfully updated", result);
    }

    @PutMapping(path = "/{eventId}/cancel")
    @PreAuthorize("hasRole('EVENT_MANAGER')")
    public ResponseEntity<Object> cancelEvent(@RequestParam Integer eventId) {
        Event cancelledEvent = eventService.cancelEvent(eventId);
        return generateResponse("Event is successfully cancelled", cancelledEvent);
    }

    // take in updatedEvent
    @PatchMapping("/{eventId}")
    @PreAuthorize("hasRole('EVENT_MANAGER')")
    public ResponseEntity<Object> patchEvent(@RequestParam Integer eventId, @RequestBody Map<String, Object> eventPatch) {
        Event event = eventService.getEventById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
    
        if (!isProvidedFieldsPatchable(eventPatch))
            throw new IllegalArgumentException("Invalid fields provided in request body");

        mapper.map(eventPatch, event);
    
        return updateEvent(event.getId(), event);
    }

    private boolean isProvidedFieldsPatchable(Map<String, Object> eventPatch) {
        return eventPatch.keySet().stream().allMatch(k -> patchableFields.contains(k));
    }

}
