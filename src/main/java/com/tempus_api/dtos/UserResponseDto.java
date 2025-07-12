package com.tempus_api.dtos;

import com.tempus_api.models.enums.Roles;

import java.time.LocalDateTime;

public record UserResponseDto (String name, String email, String cep, Roles role){
}
