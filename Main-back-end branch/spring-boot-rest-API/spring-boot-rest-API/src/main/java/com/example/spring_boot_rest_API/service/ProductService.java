package com.example.spring_boot_rest_API.service;

import com.example.spring_boot_rest_API.dto.ProductDTO;
import com.example.spring_boot_rest_API.model.Product;
import com.example.spring_boot_rest_API.model.Tag;
import com.example.spring_boot_rest_API.repository.ProductRepository;
import com.example.spring_boot_rest_API.repository.TagRepository;
import com.example.spring_boot_rest_API.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;

    public ProductService(ProductRepository productRepository, TagRepository tagRepository) {
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
    }

    public ProductDTO saveProduct(ProductDTO productDto) {
        List<Tag> tags = mapTagsByIds(productDto.getTagIds());
        Product product = ProductMapper.toEntity(productDto, tags);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDTO);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDto) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            List<Tag> tags = mapTagsByIds(productDto.getTagIds());
            product.setTags(tags);

            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setQuantity(productDto.getQuantity());

            productRepository.save(product);
            return ProductMapper.toDTO(product);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    // Fetch tag entities by IDs
    private List<Tag> mapTagsByIds(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return List.of();
        }

        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new IllegalArgumentException("Some tags not found for provided IDs");
        }

        return tags;
    }
}
