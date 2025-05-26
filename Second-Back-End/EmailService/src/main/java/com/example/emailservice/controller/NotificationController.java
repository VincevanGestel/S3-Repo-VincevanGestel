package com.example.emailservice.controller;

import com.example.emailservice.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @PostMapping("/notify")
    public ResponseEntity<Void> receiveNotification(@RequestBody ProductDTO product) {
        // Log, print, store, email, etc.
        System.out.println("Received product info: " + product.getName());
        return ResponseEntity.ok().build();
    }
}
