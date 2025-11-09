package com.example.user_service.service;

import com.example.user_service.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> users = List.of(
            new User(1L,"Ishant","ishant@gmail.com"),
            new User(2L,"joh doe","johndoe@gmail.com"),
            new User(3L,"alex","alex@gmail.com")
    );

    public List<User> getAllUsers(){
        return users;
    }

    public Optional<User> getUserById(Long id){
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }
}
