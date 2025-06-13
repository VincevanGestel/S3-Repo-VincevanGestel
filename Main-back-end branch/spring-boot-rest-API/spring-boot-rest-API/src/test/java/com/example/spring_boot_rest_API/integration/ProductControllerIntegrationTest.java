package com.example.spring_boot_rest_API.integration;

import com.example.spring_boot_rest_API.dto.ProductDTO;
import com.example.spring_boot_rest_API.model.Role;
import com.example.spring_boot_rest_API.model.Tag;
import com.example.spring_boot_rest_API.model.User;
import com.example.spring_boot_rest_API.repository.ProductRepository;
import com.example.spring_boot_rest_API.repository.RoleRepository;
import com.example.spring_boot_rest_API.repository.TagRepository;
import com.example.spring_boot_rest_API.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private TagRepository tagRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private ObjectMapper objectMapper;

    private Long existingTagId;

    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_PASSWORD = "testpass";

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
        tagRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        Tag tag = new Tag("Electronics");
        tagRepository.save(tag);
        existingTagId = tag.getId();

        Role role = new Role();
        role.setName("USER");
        roleRepository.save(role);

        User user = new User();
        user.setUsername(TEST_USERNAME);
        user.setPassword(passwordEncoder.encode(TEST_PASSWORD));
        user.setRoles(Set.of(role));
        userRepository.save(user);
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

        var mvcResult = mockMvc.perform(post("/api/products")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Laptop"))
                .andExpect(jsonPath("$.price").value(1200.0));
    }

    @Test
    void shouldReturnProductInListAfterCreation() throws Exception {
        ProductDTO dto = buildProductDTO("Phone", 699.0);
        String json = objectMapper.writeValueAsString(dto);

        // POST with async handling
        var mvcResult = mockMvc.perform(post("/api/products")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk());

        // GET is synchronous, no async needed
        mockMvc.perform(get("/api/products")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Phone"));
    }

    @Test
    void shouldFetchProductById() throws Exception {
        ProductDTO dto = buildProductDTO("Phone", 699.99);
        String json = objectMapper.writeValueAsString(dto);

        // Async POST to create product
        var mvcResult = mockMvc.perform(post("/api/products")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(request().asyncStarted())
                .andReturn();

        String response = mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO savedProduct = objectMapper.readValue(response, ProductDTO.class);

        // GET is synchronous
        mockMvc.perform(get("/api/products/" + savedProduct.getId())
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Phone"))
                .andExpect(jsonPath("$.price").value(699.99));
    }

    @Test
    void shouldUpdateProductSuccessfully() throws Exception {
        ProductDTO dto = buildProductDTO("Monitor", 200.0);
        String postJson = objectMapper.writeValueAsString(dto);

        // Async POST to create product
        var mvcResult = mockMvc.perform(post("/api/products")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(request().asyncStarted())
                .andReturn();

        String response = mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO saved = objectMapper.readValue(response, ProductDTO.class);

        saved.setPrice(180.0);
        saved.setDescription("Updated description");
        String putJson = objectMapper.writeValueAsString(saved);

        // PUT is synchronous
        mockMvc.perform(put("/api/products/" + saved.getId())
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
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

        // Async POST to create product
        var mvcResult = mockMvc.perform(post("/api/products")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(request().asyncStarted())
                .andReturn();

        String response = mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO saved = objectMapper.readValue(response, ProductDTO.class);

        // DELETE is synchronous
        mockMvc.perform(delete("/api/products/" + saved.getId())
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD)))
                .andExpect(status().isNoContent());

        // GET is synchronous
        mockMvc.perform(get("/api/products/" + saved.getId())
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404ForNonExistentProduct() throws Exception {
        // GET is synchronous
        mockMvc.perform(get("/api/products/999999")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailToCreateProductWithoutName() throws Exception {
        ProductDTO dto = buildProductDTO(null, 100.0); // Missing name
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/products")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailToCreateProductWithNegativePrice() throws Exception {
        ProductDTO dto = buildProductDTO("Invalid Product", -50.0);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/products")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAssociateTagsWithProduct() throws Exception {
        ProductDTO dto = buildProductDTO("Tagged Product", 250.0);
        String json = objectMapper.writeValueAsString(dto);

        // Async POST to create product
        var mvcResult = mockMvc.perform(post("/api/products")
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(request().asyncStarted())
                .andReturn();

        String response = mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductDTO saved = objectMapper.readValue(response, ProductDTO.class);

        // GET is synchronous
        mockMvc.perform(get("/api/products/" + saved.getId())
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tagIds[0]").value(existingTagId));
    }
}
