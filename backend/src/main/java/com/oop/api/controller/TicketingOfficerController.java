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

import com.oop.api.model.TicketingOfficer;
import com.oop.api.service.TicketingOfficerService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ticketing_officers")
public class TicketingOfficerController {

    @Autowired
    private TicketingOfficerService ticketingOfficerService;

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<Object> getAllTicketingOfficers() {
        Iterable<TicketingOfficer> ticketingOfficers = ticketingOfficerService.getAllTicketingOfficers();
        return generateResponse(ticketingOfficers);
    }

    @GetMapping(path = "/{username}")
    public @ResponseBody Optional<TicketingOfficer> getTicketingOfficer(@RequestParam String email) {
        Optional<TicketingOfficer> ticketingOfficer = ticketingOfficerService.getTicketingOfficerByEmail(email);

        if (ticketingOfficer.isEmpty())
            throw new EntityNotFoundException("Ticketing Officer not found");

        return ticketingOfficer;
    }

    // @PostMapping(path = "")
    // public ResponseEntity<Object> addNewTicketingOfficer(@Valid @RequestBody TicketingOfficer ticketingOfficer) {

    //     try {
    //         ticketingOfficerService.addNewTicketingOfficer(ticketingOfficer);
    //     } catch (DataIntegrityViolationException e) {
    //         return generateResponse("Account already exists. Please use a different email.", (Object) ticketingOfficer);
    //     }
        
    //     return generateResponse("Account is successfully created", (Object) ticketingOfficer);
    // }
    
    
}