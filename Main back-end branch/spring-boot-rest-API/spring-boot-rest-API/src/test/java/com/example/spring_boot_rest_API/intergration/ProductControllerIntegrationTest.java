package com.example.spring_boot_rest_API.intergration;

import com.example.spring_boot_rest_API.dto.ProductDTO;
import com.example.spring_boot_rest_API.model.Tag;
import com.example.spring_boot_rest_API.repository.ProductRepository;
import com.example.spring_boot_rest_API.repository.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingTagId;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll(); // Remove products first
        tagRepository.deleteAll();     // Now it's safe to delete tags

        Tag tag = new Tag("Electronics");
        tagRepository.save(tag);
        existingTagId = tag.getId();
    }

    @Test
    void testCreateAndFetchProduct() throws Exception {
        ProductDTO dto = new ProductDTO();
        dto.setName("Test Laptop");
        dto.setPrice(1200.0);
        dto.setDescription("A test product");
        dto.setQuantity(3);
        dto.setTagIds(List.of(existingTagId));

        String json = objectMapper.writeValueAsString(dto);

        // Perform POST request
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Laptop"))
                .andExpect(jsonPath("$.price").value(1200.0));

        // Perform GET request
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Laptop"));
    }

    @Test
    void testGetProductById() throws Exception {
        ProductDTO dto = new ProductDTO();
        dto.setName("Phone");
        dto.setPrice(699.99);
        dto.setDescription("A test phone");
        dto.setQuantity(10);
        dto.setTagIds(List.of(existingTagId));

        String json = objectMapper.writeValueAsString(dto);

        // Save product
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO savedProduct = objectMapper.readValue(response, ProductDTO.class);

        // Fetch by ID
        mockMvc.perform(get("/api/products/" + savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Phone"))
                .andExpect(jsonPath("$.price").value(699.99));
    }

    @Test
    void testUpdateProduct() throws Exception {
        // Create initial product
        ProductDTO dto = new ProductDTO();
        dto.setName("Monitor");
        dto.setPrice(200.0);
        dto.setDescription("Old description");
        dto.setQuantity(5);
        dto.setTagIds(List.of(existingTagId));

        String postJson = objectMapper.writeValueAsString(dto);

        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO saved = objectMapper.readValue(response, ProductDTO.class);

        // Update it
        saved.setPrice(180.0);
        saved.setDescription("Updated description");
        String putJson = objectMapper.writeValueAsString(saved);

        mockMvc.perform(put("/api/products/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(180.0))
                .andExpect(jsonPath("$.description").value("Updated description"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        ProductDTO dto = new ProductDTO();
        dto.setName("Tablet");
        dto.setPrice(300.0);
        dto.setDescription("To be deleted");
        dto.setQuantity(2);
        dto.setTagIds(List.of(existingTagId));

        String json = objectMapper.writeValueAsString(dto);

        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO saved = objectMapper.readValue(response, ProductDTO.class);

        // Delete
        mockMvc.perform(delete("/api/products/" + saved.getId()))
                .andExpect(status().isNoContent());

        // Try to fetch again
        mockMvc.perform(get("/api/products/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetNonExistentProductReturns404() throws Exception {
        mockMvc.perform(get("/api/products/999999"))
                .andExpect(status().isNotFound());
    }

}
