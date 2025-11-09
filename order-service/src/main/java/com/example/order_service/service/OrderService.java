package com.example.order_service.service;


import com.example.order_service.client.UserClient;
import com.example.order_service.model.Order;
import com.example.order_service.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    private UserClient client;
    public OrderService(UserClient client){this.client = client;}

    public List<UserDto> getAllUsersFromUserService(){
        return client.getAllUsers();
    }

    public UserDto getUserById(Long id){
        return client.getUserById(id);
    }

    public Order createFakeOrder(Long userId){
        return new Order(1L,userId,"Laptop",1);
    }
}
