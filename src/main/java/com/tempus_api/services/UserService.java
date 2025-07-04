package com.tempus_api.services;

import com.tempus_api.models.User;
import com.tempus_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

    public User findById(UUID id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot be found user"));
    }
}
