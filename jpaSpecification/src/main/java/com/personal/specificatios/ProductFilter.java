package com.personal.specificatios;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductFilter {
    private String name;
    private String category;
    private String size;
}
