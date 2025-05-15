package com.example.spring_boot_rest_API.mapper;

import com.example.spring_boot_rest_API.dto.UserDTO;
import com.example.spring_boot_rest_API.model.Role;
import com.example.spring_boot_rest_API.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return dto;
    }

    public static User toEntity(UserDTO dto, Set<Role> roles, String encodedPassword) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encodedPassword);
        user.setRoles(roles);
        return user;
    }
}
