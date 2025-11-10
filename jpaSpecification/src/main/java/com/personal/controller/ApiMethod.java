package com.personal.controller;


import com.personal.model.Product;
import com.personal.paginations.PageDTO;
import com.personal.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ApiMethod {

    private final ProductService productService;

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> filter){
        Page<Product> productOperations = productService.findProductOperations(filter);
        PageDTO pageDTO = new PageDTO(productOperations);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("getReport/{startDate}/{endDate}")
    public ResponseEntity<?> getReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate
    ){
        log.debug("startDate: {}, endDate: {}", startDate, endDate);
        return ResponseEntity.ok(
                productService.findProductReports(startDate, endDate)
        );
    }

}
