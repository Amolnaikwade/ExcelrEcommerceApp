package com.ecommerce.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.User;
import com.ecommerce.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user.
     *
     * @param user The user to be registered.
     * @param result BindingResult to handle validation errors.
     * @return ResponseEntity with status and message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            // Collect validation errors
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach(error -> errors.put(error.getObjectName(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        
        try {
            User registeredUser = userService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "User registration failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Logs in a user (a basic example, real implementations should handle JWT tokens or sessions).
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return ResponseEntity with status and message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isEmpty() || !password.equals(userOptional.get().getPassword())) {
            return new ResponseEntity<>(Map.of("error", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(Map.of("message", "Login successful"), HttpStatus.OK);
    }
}
