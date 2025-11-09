package com.example.order_service.client;


import com.example.order_service.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "user-service",
        url = "http://localhost:8080",
        path = "/api/users"
)
public interface UserClient {

    @GetMapping
    List<UserDto> getAllUsers();

    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

}
