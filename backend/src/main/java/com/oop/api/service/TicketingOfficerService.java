package com.oop.api.service;
import java.util.Optional;

// import org.hibernate.event.spi.TicketingOfficer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.api.model.TicketingOfficer;
import com.oop.api.repository.TicketingOfficerRepository;

@Service
public class TicketingOfficerService {
    @Autowired
    private TicketingOfficerRepository ticketingOfficerRepository;

    public Iterable<TicketingOfficer> getAllTicketingOfficers(){
        return ticketingOfficerRepository.findAll();
    }

    public Optional<TicketingOfficer> getTicketingOfficerByEmail(String event_manager_email){
        return ticketingOfficerRepository.findByEmail(event_manager_email);
    }


    public void addNewTicketingOfficer(TicketingOfficer ticketingOfficer){
        ticketingOfficerRepository.save(ticketingOfficer);
    }
    
}
