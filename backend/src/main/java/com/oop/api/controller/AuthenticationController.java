package com.oop.api.controller;

import com.oop.api.model.Customer;
import com.oop.api.model.EventManager;
import com.oop.api.model.TicketingOfficer;
import com.oop.api.model.User;
import com.oop.api.dto.LoginUserDTO;
import com.oop.api.dto.RegisterUserDTO;
import com.oop.api.responses.LoginResponse;
import com.oop.api.service.AuthenticationService;
import com.oop.api.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Customer> registerCustomer(@RequestBody RegisterUserDTO registerUserDto) {
        Customer registeredCustomer = authenticationService.signupCustomer(registerUserDto);

        return ResponseEntity.ok(registeredCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedCustomer = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedCustomer);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/create_event_managers")
    public ResponseEntity<EventManager> registerEventManager(@RequestBody RegisterUserDTO registerUserDto) {
        EventManager registeredEventManager = authenticationService.signupEventManager(registerUserDto);

        return ResponseEntity.ok(registeredEventManager);
    }
    
    @PostMapping("/create_ticketing_officers")
    @PreAuthorize("hasRole('EVENT_MANAGER')")
    public ResponseEntity<TicketingOfficer> registerTicketingOfficer(@RequestBody RegisterUserDTO registerUserDto) {
        TicketingOfficer registeredTicketingOfficer = authenticationService.signupTicketingOfficer(registerUserDto);

        return ResponseEntity.ok(registeredTicketingOfficer);
    }

}