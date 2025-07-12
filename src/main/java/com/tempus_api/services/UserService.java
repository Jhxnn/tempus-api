package com.tempus_api.services;

import com.tempus_api.dtos.AuthDto;
import com.tempus_api.dtos.AuthResponseDto;
import com.tempus_api.dtos.RegisterDto;
import com.tempus_api.dtos.UserResponseDto;
import com.tempus_api.exceptions.BadRequestException;
import com.tempus_api.exceptions.ConflictException;
import com.tempus_api.infra.security.TokenService;
import com.tempus_api.models.User;
import com.tempus_api.models.enums.Roles;
import com.tempus_api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User findById(UUID id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot be found user"));
    }

    public AuthResponseDto login(AuthDto authDto){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        var auth = this.authenticationManager   .authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return new AuthResponseDto(auth.getName(),token);

    }

    public UserResponseDto register(RegisterDto registerDto) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=<>?{}\\[\\]~;:.,-]).{8,}$";
        String cpfCnpjRegex = "^\\d{11}$|^\\d{14}$";

        if(userRepository.existsByEmail(registerDto.email())){
            throw new ConflictException("The email is already exist");

        }

        if (!registerDto.email().matches(emailRegex)) {
            throw new BadRequestException("Invalid email format");
        }

        if (!registerDto.password().matches(passwordRegex)) {
            throw new BadRequestException("Password must be at least 8 characters long, contain one uppercase letter and one special character");
        }


        String encryptedPass = new BCryptPasswordEncoder().encode(registerDto.password());
        User user = new User();
        BeanUtils.copyProperties(registerDto, user);
        user.setRole(Roles.COMMON);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(encryptedPass);
        userRepository.save(user);
        return new UserResponseDto(user.getName(), user.getEmail(), user.getCep(), user.getRole(), user.getUserId());
    }

    public UserResponseDto updateUser(RegisterDto registerDto, UUID id) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=<>?{}\\[\\]~;:.,-]).{8,}$";
        String cpfCnpjRegex = "^\\d{11}$|^\\d{14}$";


        if(userRepository.existsByEmail(registerDto.email())){
            throw new ConflictException("The email is already exist");

        }

        if (!registerDto.email().matches(emailRegex)) {
            throw new BadRequestException("Invalid email format");
        }

        if (!registerDto.password().matches(passwordRegex)) {
            throw new BadRequestException("Password must be at least 8 characters long, contain one uppercase letter and one special character");
        }

        String encryptedPass = new BCryptPasswordEncoder().encode(registerDto.password());
        User user = findById(id);
        BeanUtils.copyProperties(registerDto, user);
        user.setRole(Roles.COMMON);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return new UserResponseDto(user.getName(), user.getEmail(), user.getCep(), user.getRole(), user.getUserId());
    }

    public List<UserResponseDto> findAll(){
        List<User>  users = userRepository.findAll();
        List<UserResponseDto> usersResponse = new ArrayList<>();
        for(User user : users){
            usersResponse.add(new UserResponseDto(user.getName(),user.getEmail(),user.getCep(),user.getRole(),user.getUserId()));
        }
        return usersResponse;
    }

}
