package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.model.Customer;
import com.oop.api.model.User;
import com.oop.api.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<Object> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return generateResponse(users);
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    // @GetMapping(path = "/{id}")
    // public @ResponseBody Optional<User> getUser(@PathVariable Integer id) {
    //     Optional<User> user = userService.getUserById(id);

    //     if (user.isEmpty())
    //         throw new EntityNotFoundException("User not found");

    //     return user;
    // }

    // @PostMapping(path = "")
    // public ResponseEntity<Object> addNewUser(@Valid @RequestBody User user) {

    //     try {
    //         userService.addNewUser(user);
    //     } catch (DataIntegrityViolationException e) {
    //         return generateResponse("Account already exists. Please use a different email.", (Object) user);
    //     }
        
    //     return generateResponse("Account is successfully created", (Object) user);
    // }
}
