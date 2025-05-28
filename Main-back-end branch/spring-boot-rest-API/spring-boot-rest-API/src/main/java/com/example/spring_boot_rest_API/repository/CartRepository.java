package com.example.spring_boot_rest_API.repository;

import com.example.spring_boot_rest_API.model.Cart;
import com.example.spring_boot_rest_API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
