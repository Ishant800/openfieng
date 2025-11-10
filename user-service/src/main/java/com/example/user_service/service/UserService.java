package com.example.user_service.service;

import com.example.user_service.Repository.OrderRepository;
import com.example.user_service.Repository.UserRepository;
import com.example.user_service.model.Order;
import com.example.user_service.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository,OrderRepository orderRepository){
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    //create users + orders in one transactions
    @Transactional
    public User createUsersWithOrders(User user, List<Order> orders){
        orders.forEach(user::addOrder);
        return userRepository.save(user);
    }

   @Transactional
    public void updateEmail(Long userId,String newEmail){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("user not found"));
        user.setEmail(newEmail); // dirty-check update on tx commit
   }

   @Transactional
    public void removeOrderFromUser(Long userId,Long orderId){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("user not found"));
        user.getOrders().removeIf(o->o.getOrderId().equals(orderId));

        //orphanRemove deletes the order row on commit
    }


    @Transactional(readOnly = true)
    public List<User> fetchUsersFetchJoin(){
        return userRepository.findAllWithOrdersFetchJoin();
    }

    @Transactional(readOnly = true)
    public List<User> fetchAllUsersEntityGraph(){return userRepository.findAllWithOrdersEntityGraph();}

    public Optional<User> findById(Long id){return userRepository.findById(id);}

}
