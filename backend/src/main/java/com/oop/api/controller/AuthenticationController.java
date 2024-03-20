package com.oop.api.controller;

import com.oop.api.model.Customer;
import com.oop.api.dto.LoginUserDTO;
import com.oop.api.dto.RegisterUserDTO;
import com.oop.api.responses.LoginResponse;
import com.oop.api.service.AuthenticationService;
import com.oop.api.service.JwtService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Customer> register(@RequestBody RegisterUserDTO registerUserDto) {
        Customer registeredCustomer = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        Customer authenticatedCustomer = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedCustomer);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}