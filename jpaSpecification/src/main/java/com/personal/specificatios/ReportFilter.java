package com.personal.specificatios;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReportFilter {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
