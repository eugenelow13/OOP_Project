package com.oop.api.service;

// import com.oop.api.dto.ConfirmPasswordDTO;
import com.oop.api.dto.LoginUserDTO;
import com.oop.api.dto.RegisterUserDTO;
import com.oop.api.model.*;
import com.oop.api.repository.*;

import java.util.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EventManagerRepository eventManagerRepository;
    private final TicketingOfficerRepository ticketingOfficerRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(
        UserRepository userRepository,
        CustomerRepository customerRepository,
        EventManagerRepository eventManagerRepository,
        TicketingOfficerRepository ticketingOfficerRepository,
        RoleRepository roleRepository,
        TokenRepository tokenRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.eventManagerRepository = eventManagerRepository;
        this.ticketingOfficerRepository = ticketingOfficerRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    public void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public Customer signupCustomer(RegisterUserDTO input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.CUSTOMER);
            
        if (optionalRole.isEmpty()) {
            return null;
        }
            
        var customer = new Customer();
        customer.setFullName(input.getFullName());
        customer.setEmail(input.getEmail());
        customer.setPassword(passwordEncoder.encode(input.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(optionalRole.get());
        customer.setRoles(roles);

        return customerRepository.save(customer);
    }

    public TicketingOfficer signupTicketingOfficer(RegisterUserDTO input) {
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

    public EventManager signupEventManager(RegisterUserDTO input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.EVENT_MANAGER);
            
        if (optionalRole.isEmpty()) {
            return null;
        }
            
        var eventManager = new EventManager();
        eventManager.setFullName(input.getFullName());
        eventManager.setEmail(input.getEmail());
        eventManager.setPassword(passwordEncoder.encode(input.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(optionalRole.get());
        eventManager.setRoles(roles);

        return eventManagerRepository.save(eventManager);
    }

    public User authenticate(LoginUserDTO input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        String userEmail = authentication.getName();
        User optionalUser = userRepository.findByEmail(userEmail).orElseThrow();

        // String jwtToken = jwtService.generateToken(optionalUser);
        // revokeAllTokenByUser(optionalUser);
        // saveUserToken(jwtToken, optionalUser);

        // System.out.println("my token1 is:" + jwtToken);

        return optionalUser;
    }

    public boolean verifyPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
