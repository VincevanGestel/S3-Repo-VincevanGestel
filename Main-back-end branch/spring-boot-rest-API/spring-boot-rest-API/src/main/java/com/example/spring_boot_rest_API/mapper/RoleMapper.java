package com.example.spring_boot_rest_API.mapper;

import com.example.spring_boot_rest_API.dto.RoleDTO;
import com.example.spring_boot_rest_API.model.Role;

public class RoleMapper {

    public static RoleDTO toDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    public static Role toEntity(RoleDTO dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        return role;
    }
}
