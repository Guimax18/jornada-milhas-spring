package com.jornada_milhas.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jornada_milhas.users.Users;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    private final Map<String, String> RESPONSE = new HashMap<>();

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDto dto) throws Exception {
        RESPONSE.put("token", service.login(dto));
        return ResponseEntity.ok(RESPONSE);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Users user) throws Exception {
        RESPONSE.put("token", service.register(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(RESPONSE);
    }
}
