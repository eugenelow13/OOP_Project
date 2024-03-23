package com.oop.api.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oop.api.dto.RegisterUserDTO;
import com.oop.api.model.RoleEnum;
import com.oop.api.model.Role;
import com.oop.api.repository.RoleRepository;
import com.oop.api.model.TicketingOfficer;
import com.oop.api.repository.TicketingOfficerRepository;

@Service
public class TicketingOfficerService {
    @Autowired
    private TicketingOfficerRepository ticketingOfficerRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public Iterable<TicketingOfficer> getAllTicketingOfficers(){
        return ticketingOfficerRepository.findAll();
    }

    public Optional<TicketingOfficer> getTicketingOfficerByEmail(String event_manager_email){
        return ticketingOfficerRepository.findByEmail(event_manager_email);
    }
    
    public TicketingOfficer createTicketingOfficer(RegisterUserDTO input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.TICKETING_OFFICER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var ticketingOfficer = new TicketingOfficer();
        ticketingOfficer.setFullName(input.getFullName());
        ticketingOfficer.setEmail(input.getEmail());
        ticketingOfficer.setPassword(passwordEncoder.encode(input.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(optionalRole.get());
        ticketingOfficer.setRoles(roles);

        return ticketingOfficerRepository.save(ticketingOfficer);
    }
}