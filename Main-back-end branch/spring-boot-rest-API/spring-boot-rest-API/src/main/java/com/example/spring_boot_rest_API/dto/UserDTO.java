package com.example.spring_boot_rest_API.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private Set<String> roles; // optional input, or populated on return
}
