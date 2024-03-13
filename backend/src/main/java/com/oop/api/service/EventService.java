package com.oop.api.service;
import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oop.api.model.Event;
import com.oop.api.repository.EventRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Iterable<Event> getAllEvents(){
        return eventRepository.findAll();
    }
    
    public Optional<Event> getEventByName(String event_name){
        return eventRepository.findByName(event_name);
    }

    public void addNewEvent(Event event){
        eventRepository.save(event);
    }

    public Event updateEvent(Event updatedEvent) {
        // Check if the event with the given ID exists
        Event existingEvent = eventRepository.findById(updatedEvent.getId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        // Update the fields with the new values
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setType(updatedEvent.getType());
        existingEvent.setVenue(updatedEvent.getVenue());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setTicketPrice(updatedEvent.getTicketPrice());
        existingEvent.setCancellationFee(updatedEvent.getCancellationFee());
        existingEvent.setTicketsAvailable(updatedEvent.getTicketsAvailable());
        existingEvent.setCustomerAttendance(updatedEvent.getCustomerAttendance());

        // Save the updated event
        return eventRepository.save(existingEvent);
    }

    public Event setCancellationFee(Integer eventId, double newCancellationFee) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setCancellationFee(newCancellationFee);
            return eventRepository.save(event);
        } else {
            throw new EntityNotFoundException("Event not found");
        }
    }

    public Event updateAttendence(Integer eventId, Integer newAttendance) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.updateAttendance(newAttendance);
            return eventRepository.save(event);
        } else {
            throw new EntityNotFoundException("Event not found");
        }
    }

    public Event updateTicketsAvailable(Integer eventId, Integer newTicketsAvailable) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.updateAttendance(newTicketsAvailable);
            return eventRepository.save(event);
        } else {
            throw new EntityNotFoundException("Event not found");
        }
    }


}
