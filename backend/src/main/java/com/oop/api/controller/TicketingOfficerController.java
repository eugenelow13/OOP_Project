package com.oop.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.oop.api.model.TicketingOfficer;
import com.oop.api.service.TicketingOfficerService;
import com.oop.api.dto.RegisterUserDTO;
import jakarta.persistence.EntityNotFoundException;

/**
 * The TicketingOfficerController class is responsible for handling HTTP requests related to ticketing officers.
 * It provides endpoints for retrieving, creating, and deleting ticketing officers.
 */

@RestController
@RequestMapping("/ticketing_officers")
public class TicketingOfficerController {

    @Autowired
    private TicketingOfficerService ticketingOfficerService;

    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<TicketingOfficer>> getAllTicketingOfficers() {
        Iterable<TicketingOfficer> ticketingOfficers = ticketingOfficerService.getAllTicketingOfficers();
        return ResponseEntity.ok(ticketingOfficers);
    }

    @GetMapping(path = "")
    public @ResponseBody Optional<TicketingOfficer> getTicketingOfficer(@RequestParam String email) {
        Optional<TicketingOfficer> ticketingOfficer = ticketingOfficerService.getTicketingOfficerByEmail(email);

        if (ticketingOfficer.isEmpty())
            throw new EntityNotFoundException("Ticketing Officer not found");

        return ticketingOfficer;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TicketingOfficer> authenticatedTicketingOfficer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        TicketingOfficer currentTicketingOfficer = (TicketingOfficer) authentication.getPrincipal();

        return ResponseEntity.ok(currentTicketingOfficer);
    }

    @PostMapping("/create_ticketing_officers")
    @PreAuthorize("hasRole('EVENT_MANAGER')")
    public ResponseEntity<TicketingOfficer> registerTicketingOfficer(@RequestBody RegisterUserDTO registerUserDto) {
        TicketingOfficer registeredTicketingOfficer = ticketingOfficerService.createTicketingOfficer(registerUserDto);

        return ResponseEntity.ok(registeredTicketingOfficer);
    }

    @DeleteMapping("/delete_ticketing_officer")
    @PreAuthorize("hasRole('EVENT_MANAGER')")
    public ResponseEntity<Iterable<TicketingOfficer>> deleteTicketingOfficer(@RequestParam String email) {
        
        ticketingOfficerService.deleteTicketingOfficer(email);

        Iterable<TicketingOfficer> remainingTicketingOfficers = ticketingOfficerService.getAllTicketingOfficers();


        if (ticketingOfficerService.getTicketingOfficerByEmail(email).isPresent())
            throw new EntityNotFoundException("Ticketing Officer not deleted");

        return ResponseEntity.ok(remainingTicketingOfficers);
    }

}