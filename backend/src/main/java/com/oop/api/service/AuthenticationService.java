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

/**
 * The AuthenticationService class provides methods for user authentication and token management.
 * It interacts with the UserRepository, CustomerRepository, EventManagerRepository, TicketingOfficerRepository,
 * RoleRepository, TokenRepository, PasswordEncoder, AuthenticationManager, and JwtService.
 */
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

    /**
     * This class represents the authentication service in the application.
     * It is responsible for handling user authentication and authorization.
     */
    public AuthenticationService( UserRepository userRepository, CustomerRepository customerRepository,
        EventManagerRepository eventManagerRepository, TicketingOfficerRepository ticketingOfficerRepository,
        RoleRepository roleRepository, TokenRepository tokenRepository,
        AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
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

    /**
     * Revokes all tokens associated with the specified user.
     *
     * @param user the user whose tokens need to be revoked
     */
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

    /**
     * Saves the user token in the database.
     * 
     * @param jwt The JSON Web Token to be saved.
     * @param user The user associated with the token.
     */
    public void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    /**
     * Registers a new customer.
     * @param input
     * @return the registered customer
     */
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

    /**
     * Authenticates the user with the provided login credentials.
     * @param input
     * @return the authenticated user
     */
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

        return optionalUser;
    }

    /**
     * Verifies if the provided password matches the user's password.
     *
     * @param user     the user object containing the password to be verified
     * @param password the password to be checked against the user's password
     * @return true if the provided password matches the user's password, false otherwise
     */
    public boolean verifyPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
