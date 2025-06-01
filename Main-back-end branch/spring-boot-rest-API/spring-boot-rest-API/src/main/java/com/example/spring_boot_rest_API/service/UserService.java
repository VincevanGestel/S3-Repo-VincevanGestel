package com.example.spring_boot_rest_API.service;

import com.example.spring_boot_rest_API.dto.UserDTO;
import com.example.spring_boot_rest_API.mapper.UserMapper;
import com.example.spring_boot_rest_API.model.Role;
import com.example.spring_boot_rest_API.model.User;
import com.example.spring_boot_rest_API.repository.RoleRepository;
import com.example.spring_boot_rest_API.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Set<Role> rolesToAssign;

        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            rolesToAssign = userDTO.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
        } else {
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Default role USER not found"));
            rolesToAssign = new HashSet<>();
            rolesToAssign.add(userRole);
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User newUser = UserMapper.toEntity(userDTO, rolesToAssign, encodedPassword);
        User savedUser = userRepository.save(newUser);

        return UserMapper.toDTO(savedUser);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toDTO(user);
    }

}
