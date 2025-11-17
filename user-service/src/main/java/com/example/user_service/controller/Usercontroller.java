package com.example.user_service.controller;

import com.example.user_service.dto.OrderDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.model.Order;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class Usercontroller {
    private final UserService userService;

    public Usercontroller(UserService userService){this.userService = userService;}

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.fetchUsersFetchJoin().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable Long id){
        User user = userService.findById(id).orElseThrow();
        List<OrderDto> orders = user.getOrders().stream()
                .map(o-> new OrderDto(o.getOrderId(),o.getProduct(),o.getQuantity()))
                .collect(Collectors.toList());
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), orders);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable Long id){
        User user = userService.findById(id).orElseThrow(()-> new RuntimeException("user not found"));

        return ResponseEntity.ok(user);

    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateorder(@PathVariable Long id,@RequestBody Order dto){
        return ResponseEntity.ok(userService.updateOrderFromUser(id,dto));
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto payload){
        User user = new User();
        user.setEmail(payload.email());
        user.setUsername(payload.name());

        var orders = payload.orders().stream()
                .map(o-> new Order(null,o.product(),o.quantity(),null))
                .toList();

        User saved = userService.createUsersWithOrders(user,orders);
        return toDto(saved);
    }

    private UserDto toDto(User user){
       var orders = user.getOrders().stream()
               .map(o-> new OrderDto(o.getOrderId(), o.getProduct(),o.getQuantity()))
               .collect(Collectors.toList());
       return new UserDto(user.getId(), user.getUsername(), user.getEmail(),orders);
    }


}
