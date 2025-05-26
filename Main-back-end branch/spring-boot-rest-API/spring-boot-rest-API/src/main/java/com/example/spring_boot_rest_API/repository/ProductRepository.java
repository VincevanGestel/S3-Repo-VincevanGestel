package com.example.spring_boot_rest_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_boot_rest_API.model.Product;

//@Repository //by default already behaves as a repository thanks to the extends function
public interface ProductRepository extends JpaRepository<Product, Long> {
//try writing custom query
}
