package com.example.spring_boot_rest_API.service;

import com.example.spring_boot_rest_API.dto.ProductDTO;
import com.example.spring_boot_rest_API.model.Product;
import com.example.spring_boot_rest_API.model.Tag;
import com.example.spring_boot_rest_API.repository.ProductRepository;
import com.example.spring_boot_rest_API.repository.TagRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;
    private List<Tag> tagList;
    private List<Long> tagIds;

    @BeforeEach
    void setUp() {
        Tag tag = new Tag("Tech");
        tag.setId(1L);
        tagList = List.of(tag);
        tagIds = List.of(1L);

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1000.0);
        product.setDescription("A high-end laptop.");
        product.setQuantity(5);
        product.setTags(tagList);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Laptop");
        productDTO.setPrice(1000.0);
        productDTO.setDescription("A high-end laptop.");
        productDTO.setQuantity(5);
        productDTO.setTagIds(tagIds);
    }

    @Test
    void failingTest()
    {
        assertEquals(1,0);
    }
    @Test
    void testSaveProduct() {
        when(tagRepository.findAllById(tagIds)).thenReturn(tagList);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO saved = productService.saveProduct(productDTO);

        assertEquals(productDTO.getId(), saved.getId());
        assertEquals(productDTO.getName(), saved.getName());
        assertEquals(productDTO.getDescription(), saved.getDescription());
        assertEquals(productDTO.getPrice(), saved.getPrice());
        assertEquals(productDTO.getQuantity(), saved.getQuantity());

        verify(tagRepository).findAllById(tagIds);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        verify(productRepository).findAll();
    }

    @Test
    void testGetProductByIdFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<ProductDTO> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ProductDTO> result = productService.getProductById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(tagRepository.findAllById(tagIds)).thenReturn(tagList);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO updated = productService.updateProduct(1L, productDTO);

        assertEquals("Laptop", updated.getName());
        verify(tagRepository).findAllById(tagIds);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testDeleteProductById() {
        productService.deleteProductById(1L);
        verify(productRepository).deleteById(1L);
    }
}
