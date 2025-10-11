package com.personal.controller;


import com.personal.dto.RegisterRequest;
import com.personal.service.KeycloakUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KeycloakUserService keycloakUserService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        keycloakUserService.registerUser(request);
        return ResponseEntity.ok("User registered successfully in Keycloak");
    }
}
