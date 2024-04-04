package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.dto.TicketInfo;
import com.oop.api.dto.TicketInfoWithBooking;
import com.oop.api.model.Ticket;
import com.oop.api.service.TicketService;

import jakarta.persistence.EntityNotFoundException;

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
    public @ResponseBody ResponseEntity<Object> admitTicket(@PathVariable Integer ticketId) {
        ticketService.markTicketAsAdmitted(ticketId);

       return getEvent(ticketId);
    }

}
