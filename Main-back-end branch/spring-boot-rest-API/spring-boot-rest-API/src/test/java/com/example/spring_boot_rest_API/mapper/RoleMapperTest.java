package com.example.spring_boot_rest_API.mapper;

import com.example.spring_boot_rest_API.dto.RoleDTO;
import com.example.spring_boot_rest_API.model.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoleMapperTest {

    @Test
    public void testToDTO_MapsRoleToDTO_Correctly() {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        // Act
        RoleDTO dto = RoleMapper.toDTO(role);

        // Assert
        assertEquals(role.getId(), dto.getId());
        assertEquals(role.getName(), dto.getName());
    }

    @Test
    public void testToDTO_HandlesNullInput() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> RoleMapper.toDTO(null));
    }

    @Test
    public void testToEntity_MapsDTOToRole_Correctly() {
        // Arrange
        RoleDTO dto = new RoleDTO();
        dto.setId(2L);
        dto.setName("USER");

        // Act
        Role role = RoleMapper.toEntity(dto);

        // Assert
        assertEquals(dto.getId(), role.getId());
        assertEquals(dto.getName(), role.getName());
    }

    @Test
    public void testToEntity_HandlesNullInput() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> RoleMapper.toEntity(null));
    }
}