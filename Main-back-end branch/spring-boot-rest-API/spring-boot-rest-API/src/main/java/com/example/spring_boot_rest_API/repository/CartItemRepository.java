package com.example.spring_boot_rest_API.repository;

import com.example.spring_boot_rest_API.model.Cart;
import com.example.spring_boot_rest_API.model.CartItem;
import com.example.spring_boot_rest_API.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
