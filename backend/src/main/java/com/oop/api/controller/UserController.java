package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.oop.api.model.User;
import com.oop.api.service.UserService;
import jakarta.persistence.EntityNotFoundException;

/**
 * The UserController class handles HTTP requests related to users.
 */

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/all")
    // @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public @ResponseBody ResponseEntity<Object> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return generateResponse(users);
    }

    @GetMapping(path = "")
    public @ResponseBody Optional<User> getUser(@RequestParam Integer id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty())
            throw new EntityNotFoundException("User not found");

        return user;
    }

}
