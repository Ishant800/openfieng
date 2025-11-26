package com.example.user_service.service;

import com.example.user_service.Repository.UserRepository;
import com.example.user_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User auth = userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(auth.getEmail())
                .password(auth.getPassword())
                .roles(auth.getRole())
                .build();
    }
}
