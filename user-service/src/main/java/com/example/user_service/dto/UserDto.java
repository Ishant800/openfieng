package com.example.user_service.dto;

import java.util.List;

public record UserDto(Long id, String name, String email, List<OrderDto> orders){}
