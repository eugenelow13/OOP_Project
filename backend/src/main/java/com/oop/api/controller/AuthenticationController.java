package com.oop.api.controller;

import com.oop.api.model.*;
import com.oop.api.dto.*;
import com.oop.api.responses.LoginResponse;
import com.oop.api.service.AuthenticationService;
import com.oop.api.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        authenticationService.revokeAllTokenByUser(authenticatedUser);
        authenticationService.saveUserToken(jwtToken, authenticatedUser);

        int id = authenticatedUser.getId();
        String fullName = authenticatedUser.getFullName();
        String email = authenticatedUser.getEmail();
        Set<Role> roles = authenticatedUser.getRoles();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setId(id);
        loginResponse.setFullName(fullName);
        loginResponse.setEmail(email);
        loginResponse.setRoles(roles);

        return ResponseEntity.ok(loginResponse);
    }

    // @PostMapping("/create_event_managers")
    // public ResponseEntity<EventManager> registerEventManager(@RequestBody RegisterUserDTO registerUserDto) {
    //     EventManager registeredEventManager = authenticationService.signupEventManager(registerUserDto);

    //     return ResponseEntity.ok(registeredEventManager);
    // }
    
    // @PostMapping("/create_ticketing_officers")
    // @PreAuthorize("hasRole('EVENT_MANAGER')")
    // public ResponseEntity<TicketingOfficer> registerTicketingOfficer(@RequestBody RegisterUserDTO registerUserDto) {
    //     TicketingOfficer registeredTicketingOfficer = authenticationService.signupTicketingOfficer(registerUserDto);

    //     return ResponseEntity.ok(registeredTicketingOfficer);
    // }

}