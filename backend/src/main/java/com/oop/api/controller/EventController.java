package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.model.Event;
import com.oop.api.service.EventService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<Object> getAllEvents() {
        Iterable<Event> events = eventService.getAllEvents();
        return generateResponse(events);
    }

    @GetMapping(path = "/findEvent")
    public @ResponseBody Optional<Event> getEvent(@RequestParam String name) {
        Optional<Event> event = eventService.getEventByName(name);

        if (event.isEmpty())
            throw new EntityNotFoundException("Event not found");

        return event;
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> addNewCustomer(@Valid @RequestBody Event event) {

        try {
            eventService.addNewCustomer(event);
        } catch (DataIntegrityViolationException e) {
            return generateResponse("Account already exists. Please use a different email.", (Object) event);
        }

        return generateResponse("Account is successfully created", (Object) event);
    }

    @PostMapping(path = "/updateEvent")
    public ResponseEntity<Object> updateEvent(@Valid @RequestBody Event updatedEvent) {
        try {
            Event result = eventService.updateEvent(updatedEvent);
            return generateResponse("Event is successfully updated", result);
        } catch (EntityNotFoundException e) {
            return generateResponse("Event not found", null);
    }

    @PostMapping("/setCancellationFee")
    public ResponseEntity<Object> setCancellationFee(@RequestParam(name = "eventId") Integer eventId, @RequestParam(name = "newCancellationFee") double newCancellationFee) {
        try {
            Event updatedEvent = eventService.setCancellationFee(eventId, newCancellationFee);
            return generateResponse("Cancellation fee updated successfully", updatedEvent);
        } catch (EntityNotFoundException e) {
            return generateResponse("Event not found", null);
    }

}
}
