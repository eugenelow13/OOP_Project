package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private HashSet<String> patchableFields = new HashSet<>(Arrays.asList("name", "type", "venue", "date", "ticketPrice", "cancellationFee", "ticketsAvailable", "customerAttendance"));

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<Object> getAllEvents() {
        Iterable<Event> events = eventService.getAllEvents();
        return generateResponse(events);
    }

    @GetMapping(path = "/{eventId}")
    public @ResponseBody ResponseEntity<Object> getEvent(@PathVariable Integer eventId) {
        Optional<Event> event = eventService.getEventById(eventId);

        if (event.isEmpty())
            throw new EntityNotFoundException("Event not found");

        return generateResponse(event.get());
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> addNewEvent(@Valid @RequestBody Event event) {
        eventService.addNewEvent(event);
        return generateResponse("Event is successfully created", (Object) event, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<Object> updateEvent(@PathVariable Integer eventId, @Valid @RequestBody Event updatedEventWithoutId) {
        updatedEventWithoutId.setId(eventId);
        Event updatedEvent = updatedEventWithoutId;

        Event result = eventService.updateEvent(updatedEvent);
        return generateResponse("Event is successfully updated", result);
    }

    // take in updatedEvent
    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> patchEvent(@PathVariable Integer eventId, @RequestBody Map<String, Object> eventPatch) {
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
