package com.tempus_api.controllers;

import com.tempus_api.dtos.AuthDto;
import com.tempus_api.dtos.AuthResponseDto;
import com.tempus_api.dtos.RegisterDto;
import com.tempus_api.dtos.UserResponseDto;
import com.tempus_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthDto authDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(authDto));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(registerDto));
    }
}
