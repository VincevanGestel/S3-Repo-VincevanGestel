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
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private TagRepository tagRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private ObjectMapper objectMapper;

    private Long existingTagId;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
        tagRepository.deleteAll();

        Tag tag = new Tag("Electronics");
        tagRepository.save(tag);
        existingTagId = tag.getId();
    }

    private ProductDTO buildProductDTO(String name, double price) {
        ProductDTO dto = new ProductDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setDescription("Sample description");
        dto.setQuantity(5);
        dto.setTagIds(List.of(existingTagId));
        return dto;
    }

    @Test
    void shouldCreateProductSuccessfully() throws Exception {
        ProductDTO dto = buildProductDTO("Test Laptop", 1200.0);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Laptop"))
                .andExpect(jsonPath("$.price").value(1200.0));
    }

    @Test
    void shouldReturnProductInListAfterCreation() throws Exception {
        ProductDTO dto = buildProductDTO("Phone", 699.0);
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Phone"));
    }

    @Test
    void shouldFetchProductById() throws Exception {
        ProductDTO dto = buildProductDTO("Phone", 699.99);
        String json = objectMapper.writeValueAsString(dto);

        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO savedProduct = objectMapper.readValue(response, ProductDTO.class);

        mockMvc.perform(get("/api/products/" + savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Phone"))
                .andExpect(jsonPath("$.price").value(699.99));
    }

    @Test
    void shouldUpdateProductSuccessfully() throws Exception {
        ProductDTO dto = buildProductDTO("Monitor", 200.0);
        String postJson = objectMapper.writeValueAsString(dto);

        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO saved = objectMapper.readValue(response, ProductDTO.class);

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
    void shouldDeleteProductAndReturnNotFoundOnFetch() throws Exception {
        ProductDTO dto = buildProductDTO("Tablet", 300.0);
        String json = objectMapper.writeValueAsString(dto);

        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO saved = objectMapper.readValue(response, ProductDTO.class);

        mockMvc.perform(delete("/api/products/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/products/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404ForNonExistentProduct() throws Exception {
        mockMvc.perform(get("/api/products/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailToCreateProductWithoutName() throws Exception {
        ProductDTO dto = buildProductDTO(null, 100.0); // Missing name
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailToCreateProductWithNegativePrice() throws Exception {
        ProductDTO dto = buildProductDTO("Invalid Product", -50.0);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAssociateTagsWithProduct() throws Exception {
        ProductDTO dto = buildProductDTO("Tagged Product", 250.0);
        String json = objectMapper.writeValueAsString(dto);

        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO saved = objectMapper.readValue(response, ProductDTO.class);

        mockMvc.perform(get("/api/products/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tagIds[0]").value(existingTagId));
    }

}
