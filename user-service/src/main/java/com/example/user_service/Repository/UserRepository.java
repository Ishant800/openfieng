package com.example.user_service.Repository;

import com.example.user_service.model.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User ,Long> {

    @Query("select u from User u left join fetch u.orders")
    List<User> findAllWithOrdersFetchJoin();

    @EntityGraph(attributePaths = {"orders"})
    @Query("select u from User u")
    List<User> findAllWithOrdersEntityGraph();
}
