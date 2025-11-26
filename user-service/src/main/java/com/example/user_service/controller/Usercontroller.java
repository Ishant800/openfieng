package com.example.user_service.controller;

import com.example.user_service.JWT.JwtService;
import com.example.user_service.Repository.UserRepository;
import com.example.user_service.dto.JwtTokenClaimsDto;
import com.example.user_service.dto.OrderDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.model.Order;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class Usercontroller {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final UserRepository repo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Usercontroller(UserService userService, JwtService jwtService, UserRepository userRepository, UserRepository repo){
        this.userService = userService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.repo = repo;


    }

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


    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){session.invalidate();}
        return "Logged out";

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


    //security testing api

    @GetMapping("/profile")
    public String profile(){
        return "User profile data";
    }


    @PostMapping("/register")
    public String register(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "user registered sucessfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){

        User users = userRepository.findByEmail(user.getEmail()).orElseThrow(()-> new RuntimeException("Invalid email!"));
        if(!encoder.matches(user.getPassword(),users.getPassword())){
            throw new RuntimeException("invalid password");
        }

        JwtTokenClaimsDto dto = new JwtTokenClaimsDto();
        dto.setUserid(users.getId());
        dto.setUsername(users.getUsername());
        dto.setEmail(users.getEmail());
        dto.setRole(users.getRole());

        return jwtService.generateToken(dto);
    }




}
