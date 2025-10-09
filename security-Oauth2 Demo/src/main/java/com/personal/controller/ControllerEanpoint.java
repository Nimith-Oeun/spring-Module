package com.personal.controller;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ControllerEanpoint {

    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok("Welcome to the home page!");
    }
}
