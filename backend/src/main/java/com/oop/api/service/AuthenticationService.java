package com.oop.api.service;

import com.oop.api.dto.LoginUserDTO;
import com.oop.api.dto.RegisterUserDTO;
import com.oop.api.model.Role;
import com.oop.api.model.RoleEnum;
import com.oop.api.model.Customer;
import com.oop.api.model.User;
import com.oop.api.repository.CustomerRepository;
import com.oop.api.repository.UserRepository;
import com.oop.api.repository.RoleRepository;

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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        CustomerRepository customerRepository,
        RoleRepository roleRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder

    ) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer signup(RegisterUserDTO input) {
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

    public User authenticate(LoginUserDTO input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        String userEmail = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);

        // return customerRepository.findByEmail(input.getEmail())
        //         .orElseThrow();

        return optionalUser.get();
    }

}
