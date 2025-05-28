package com.example.spring_boot_rest_API.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private ProductDTO product;
    private int quantity;
}
