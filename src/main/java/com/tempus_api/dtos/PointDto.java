package com.tempus_api.dtos;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PointDto(LocalDateTime start,
                       LocalDateTime finish,
                       LocalDate date,
                       UUID employeeId) {
}
