package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.oop.api.dto.TicketInfoWithBooking;
import com.oop.api.model.Ticket;
import com.oop.api.service.TicketService;

/**
 * The TicketController class handles HTTP requests related to tickets.
 */

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TicketService ticketService;

    @GetMapping(path = "/{ticketId}")
    public @ResponseBody ResponseEntity<Object> getEvent(@PathVariable Integer ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);

        TicketInfoWithBooking ticketInfo = mapper.map(ticket, TicketInfoWithBooking.class);

        return generateResponse(ticketInfo);
    }

    @PostMapping(path = "/{ticketId}/admit")
    @PreAuthorize("hasRole('TICKETING_OFFICER')")
    public @ResponseBody ResponseEntity<Object> admitTicket(@PathVariable Integer ticketId) {
        ticketService.markTicketAsAdmitted(ticketId);

       return getEvent(ticketId);
    }

}
