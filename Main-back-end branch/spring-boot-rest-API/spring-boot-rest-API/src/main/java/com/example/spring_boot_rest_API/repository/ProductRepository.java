package com.example.spring_boot_rest_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_boot_rest_API.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@Repository //by default already behaves as a repository thanks to the extends function
public interface ProductRepository extends JpaRepository<Product, Long> {
//try writing custom query

    List<Product> findByPriceBetweenAndTags_Name(Double minPrice, Double maxPrice, String tagName);

    // Or with a custom JPQL query (for more control)
    @Query("SELECT p FROM Product p JOIN p.tags t WHERE p.price BETWEEN :minPrice AND :maxPrice AND t.name = :tagName")
    List<Product> findProductsInPriceRangeWithTag(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("tagName") String tagName
    );
}

