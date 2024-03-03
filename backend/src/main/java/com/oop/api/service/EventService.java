package com.oop.api.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.api.model.Event;
import com.oop.api.repository.EventRepository;

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

    public void addNewCustomer(Event event){
        eventRepository.save(event);
    }

     // get creditBalance of customer
    
     // update creditBalance of customer

     // delete customer

}
