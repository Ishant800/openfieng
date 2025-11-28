package com.example.product_service.service;

import com.example.product_service.Dto.UserDto;
import com.example.product_service.entity.Tenant;
import com.example.product_service.entity.User;
import com.example.product_service.repository.TenantRepository;
import com.example.product_service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, TenantRepository tenantRepository) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
    }

    public UserDto createUser(UserDto dto){
        Tenant tenant = tenantRepository.findById(dto.getTenantId()).orElseThrow(()-> new RuntimeException("tenant not found please register your business!"));
        User user = new User();
        user.setTenantId(dto.getTenantId());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setUsername(dto.getUsername());

      User saveduser =  userRepository.save(user);
      UserDto userdto = new UserDto();
      userdto.setUsername(saveduser.getUsername());
      userdto.setRole(saveduser.getRole());
      userdto.setPhone(saveduser.getPhone());
      userdto.setFullName(saveduser.getFullName());
      userdto.setTenantId(saveduser.getTenantId());
      userdto.setEmail(saveduser.getEmail());

      return userdto;

    }

}
