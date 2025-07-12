package com.tempus_api.dtos;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record PointDto(LocalTime start,
                       LocalTime finish,
                       LocalDate date,
                       UUID employeeId) {
}
