package com.example.spring_boot_rest_API.mapper;

import com.example.spring_boot_rest_API.dto.ProductDTO;
import com.example.spring_boot_rest_API.model.Product;
import com.example.spring_boot_rest_API.model.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductMapperTest {

    @Test
    public void testToDTO_MapsProductToDTO_Correctly() {
        // Arrange
        Tag tag1 = new Tag("Tech");
        tag1.setId(1L);
        Tag tag2 = new Tag("Electronics");
        tag2.setId(2L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(999.99);
        product.setDescription("High-end gaming laptop");
        product.setQuantity(10);
        product.setTags(List.of(tag1, tag2));

        // Act
        ProductDTO dto = ProductMapper.toDTO(product);

        // Assert
        assertEquals(product.getId(), dto.getId());
        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getPrice(), dto.getPrice());
        assertEquals(product.getDescription(), dto.getDescription());
        assertEquals(product.getQuantity(), dto.getQuantity());
        assertEquals(List.of(1L, 2L), dto.getTagIds()); // Verify Tag IDs are mapped correctly
    }

    @Test
    public void testToDTO_HandlesEmptyTags_Correctly() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setTags(List.of()); // Empty tags

        // Act
        ProductDTO dto = ProductMapper.toDTO(product);

        // Assert
        assertTrue(dto.getTagIds().isEmpty()); // Ensure no NPE and empty list is returned
    }

    @Test
    public void testToEntity_MapsDTOToProduct_Correctly() {
        // Arrange
        ProductDTO dto = new ProductDTO();
        dto.setName("Smartphone");
        dto.setPrice(699.99);
        dto.setDescription("Latest model");
        dto.setQuantity(5);
        dto.setTagIds(List.of(1L, 2L)); // Tag IDs to be resolved

        Tag tag1 = new Tag("Tech");
        tag1.setId(1L);
        Tag tag2 = new Tag("Mobile");
        tag2.setId(2L);
        List<Tag> tags = List.of(tag1, tag2);

        // Act
        Product product = ProductMapper.toEntity(dto, tags);

        // Assert
        assertEquals(dto.getName(), product.getName());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getQuantity(), product.getQuantity());
        assertEquals(tags, product.getTags()); // Verify Tags are set correctly
    }

    @Test
    public void testToEntity_HandlesNullTags_Correctly() {
        // Arrange
        ProductDTO dto = new ProductDTO();
        dto.setName("Smartphone");
        dto.setTagIds(null); // Explicitly set null

        // Act
        Product product = ProductMapper.toEntity(dto, null);

        // Assert
        assertEquals(dto.getName(), product.getName());
        assertNull(product.getTags()); // Ensure no NPE and null is handled
    }
}