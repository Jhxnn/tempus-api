package com.tempus_api.dtos;

import com.tempus_api.models.enums.Roles;

public record RegisterDto(String name,
                          String email,
                          String password,
                          String cep,
                          Roles role) {
}
