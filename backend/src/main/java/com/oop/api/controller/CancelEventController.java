package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oop.api.model.CancelEvent;
import com.oop.api.model.Event;
import com.oop.api.service.CancelEventService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cancelledevents")
public class CancelEventController {

    @Autowired
    private CancelEventService cancelEventService;

    @Autowired
    private ModelMapper mapper;

    private HashSet<String> patchableFields = new HashSet<>(Arrays.asList("name", "type", "venue", "date", "ticketPrice", "cancellationFee", "ticketsAvailable", "customerAttendance"));

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<Object> getAllCancelledEvents() {
        Iterable<CancelEvent> events = cancelEventService.getAllCancelledEvents();
        return generateResponse(events);
    }

    @GetMapping(path = "/{canceleventId}")
    public @ResponseBody ResponseEntity<Object> getCancelledEvent(@PathVariable Integer eventId) {
        Optional<CancelEvent> event = cancelEventService.getCancelledEventById(eventId);

        if (event.isEmpty())
            throw new EntityNotFoundException("Event not found");
        return generateResponse(event.get());
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> addNewCancelledEvent(@Valid @RequestBody CancelEvent event) {
        cancelEventService.addNewCancelledEvent(event);
        return generateResponse("Event is successfully cancelled", (Object) event, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{canceleventId}")
    public ResponseEntity<Object> updateCancelledEvent(@PathVariable Integer cancelEventId, @Valid @RequestBody CancelEvent cancelledEventWithoutId) {
        cancelledEventWithoutId.setId(cancelEventId);
        CancelEvent cancelledEvent = cancelledEventWithoutId;

        CancelEvent result = cancelEventService.cancelEvent(cancelledEvent);
        return generateResponse("Event is successfully cancelled", result);
    }

    
}