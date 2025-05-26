package com.example.spring_boot_rest_API.controller;

import com.example.spring_boot_rest_API.dto.UserDTO;
import com.example.spring_boot_rest_API.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(registeredUser);
    }

    //Login configured in the SecurityConfig (Uses base path POST /login)
}
