package com.example.spring_boot_rest_API.dto;

import lombok.Getter;
import lombok.Setter;

//import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TagDTO {
    private Long id;
    //Use extension to add @NotBlank same as using [Required]!
    private String name;
}
