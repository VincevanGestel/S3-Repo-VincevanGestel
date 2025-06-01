package com.example.spring_boot_rest_API.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO {
    private Long cartId;
    private List<CartItemDTO> items;
}
