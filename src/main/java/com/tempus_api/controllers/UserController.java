package com.tempus_api.controllers;

import com.tempus_api.dtos.AuthDto;
import com.tempus_api.dtos.AuthResponseDto;
import com.tempus_api.dtos.RegisterDto;
import com.tempus_api.dtos.UserResponseDto;
import com.tempus_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Usuário", description = "Operações de autenticação e cadastro")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Realizar login do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @Parameter(description = "Credenciais do usuário") @RequestBody AuthDto authDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(authDto));
    }

    @Operation(summary = "Registrar novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @Parameter(description = "Dados do novo usuário") @RequestBody RegisterDto registerDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(registerDto));
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @Parameter(description = "Dados do usuário") @RequestBody RegisterDto registerDto, @PathVariable(name = "id")UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(registerDto, id));
    }
}
