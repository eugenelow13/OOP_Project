package com.oop.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.model.User;
import com.oop.api.service.UserService;
import com.oop.api.util.ResponseHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    // private UserRepository userRepository;
    private UserService userService;

    @GetMapping(path = "")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<User> getUser(@PathVariable Integer id) {
        // This returns a JSON or XML with the users
        return userService.getUserById(id);
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody User user) {
        userService.addNewUser(user);
        return ResponseHandler.generateResponse("Created", (Object) user, HttpStatus.OK);
    }
}
