package com.example.spring_boot_rest_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStatusDTO {
    private String username;
    private String status;
}
