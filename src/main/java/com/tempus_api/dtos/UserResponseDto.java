package com.tempus_api.dtos;

import com.tempus_api.models.enums.Roles;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto (String name, String email, String cep, Roles role, UUID id){
}
