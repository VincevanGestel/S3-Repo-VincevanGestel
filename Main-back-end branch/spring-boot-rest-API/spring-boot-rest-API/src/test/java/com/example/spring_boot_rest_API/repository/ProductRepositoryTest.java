package com.example.spring_boot_rest_API.repository;

import com.example.spring_boot_rest_API.model.Product;
import com.example.spring_boot_rest_API.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void testFindByPriceRangeAndTagName_ReturnsProducts() {
        // Arrange
        Tag techTag = new Tag();
        techTag.setName("Tech");
        tagRepository.save(techTag);

        Product laptop = new Product();
        laptop.setName("Laptop");
        laptop.setPrice(999.99);
        laptop.setTags(List.of(techTag));

        Product phone = new Product();
        phone.setName("Phone");
        phone.setPrice(699.99);
        phone.setTags(List.of(techTag));

        productRepository.saveAll(List.of(laptop, phone));

        // Act
        List<Product> results = productRepository.findByPriceBetweenAndTags_Name(500.0, 1000.0, "Tech");

        // Assert
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(p -> p.getPrice() >= 500.0 && p.getPrice() <= 1000.0));
    }

    @Test
    public void testFindByPriceRangeAndTagName_ReturnsEmptyListForNoMatches() {
        // Act
        List<Product> results = productRepository.findByPriceBetweenAndTags_Name(10.0, 100.0, "NonExistentTag");

        // Assert
        assertTrue(results.isEmpty());
    }
}
