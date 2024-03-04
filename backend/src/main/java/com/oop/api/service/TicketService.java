package com.oop.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.oop.api.exception.TicketNotFoundException;
import com.oop.api.model.Ticket;
import com.oop.api.repository.TicketRepository;

public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public void addNewTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public void markTicketAsAdmitted(Integer id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
            
        if (!ticket.isPresent())
            throw new TicketNotFoundException("Ticket not found");

        Ticket t = ticket.get();
        t.setIsAdmitted(true);
        ticketRepository.save(t);

    }
}
