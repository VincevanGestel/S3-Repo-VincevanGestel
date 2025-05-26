package com.example.spring_boot_rest_API.mapper;

import com.example.spring_boot_rest_API.dto.ProductDTO;
import com.example.spring_boot_rest_API.model.Product;
import com.example.spring_boot_rest_API.model.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setQuantity(product.getQuantity());

        dto.setTagIds(product.getTags()
                .stream()
                .map(Tag::getId)
                .collect(Collectors.toList()));  // <-- Return tag IDs instead of names

        return dto;
    }

    public static Product toEntity(ProductDTO dto, List<Tag> tags) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setTags(tags);
        return product;
    }
}
