package com.personal.controller;


import com.personal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ApiMethod {

    private final ProductService productService;

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> filter){
        return ResponseEntity.ok(
                productService.findProductOperations(filter)
        );
    }

}
