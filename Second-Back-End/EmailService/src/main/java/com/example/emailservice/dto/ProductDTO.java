package com.example.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private Long id;

    private String name;

    private Double price;

    private String description;

    private int quantity;

    private List<Long> tagIds;  // <-- Change from List<String> to List<Long>
}
