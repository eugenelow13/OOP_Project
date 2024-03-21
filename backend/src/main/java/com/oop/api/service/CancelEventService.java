package com.oop.api.service;
import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oop.api.model.CancelEvent;
import com.oop.api.model.Event;
import com.oop.api.repository.CancelEventRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CancelEventService {
    @Autowired
    private CancelEventRepository cancelEventRepository;

    public Iterable<CancelEvent> getAllCancelledEvents(){
        return cancelEventRepository.findAll();
    }

    public CancelEvent cancelEvent(CancelEvent cancelledEvent) {
        // Check if the event with the given ID exists
        cancelEventRepository.findById(cancelledEvent.getId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        // Save the updated event
        return cancelEventRepository.save(cancelledEvent);
    }

    public Optional<CancelEvent> getCancelledEventById(Integer id){
        return cancelEventRepository.findById(id);
    }

     public void addNewCancelledEvent(CancelEvent event){
        cancelEventRepository.save(event);
    }

    
}
