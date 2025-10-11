package com.personal.controller;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ControllerEanpoint {

    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok("Welcome to home page!");
    }

    @PreAuthorize("hasAuthority('SCOPE_read:user')")
    @GetMapping("/user")
    public ResponseEntity<?> home1() {
        return ResponseEntity.ok("Welcome to user page!");
    }

    @PreAuthorize("hasAuthority('SCOPE_user:email')")
    @GetMapping("/admin")
    public ResponseEntity<?> home2() {
        return ResponseEntity.ok("Welcome to admin page!");
    }
}
