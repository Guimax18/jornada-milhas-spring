package com.jornada_milhas.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> get(Authentication authentication) {
        try {
            return ResponseEntity.ok(service.findOne(authentication.getPrincipal().toString()));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody UserDto user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(user));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> put(Authentication authentication, @RequestBody UserDto user) {
        try {
            return ResponseEntity.ok(service.update(authentication.getPrincipal().toString(), user));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
