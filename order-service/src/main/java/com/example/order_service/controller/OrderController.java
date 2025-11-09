package com.example.order_service.controller;

import com.example.order_service.model.Order;
import com.example.order_service.model.UserDto;
import com.example.order_service.service.OrderService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/users")
    public List<UserDto> getALlUsers(){return orderService.getAllUsersFromUserService();}

    @GetMapping("/users/{id}")
    public UserDto getUser(@PathVariable Long id){
        return orderService.getUserById(id);
    }

    @GetMapping("/create/{userId}")
    public Order createOrder(@PathVariable Long userId){
        return orderService.createFakeOrder(userId);
    }

}
