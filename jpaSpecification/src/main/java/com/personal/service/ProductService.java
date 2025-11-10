package com.personal.service;

import com.personal.model.Product;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ProductService {

//    List<Product> findProductOperations(Map<String, String> filter);
Page<Product> findProductOperations(Map<String, String> filter);
List<Product> findProductReports(LocalDate startDate, LocalDate endDate);
}