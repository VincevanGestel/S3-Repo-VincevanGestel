package com.example.spring_boot_rest_API.controller;

import com.example.spring_boot_rest_API.dto.CartDTO;
import com.example.spring_boot_rest_API.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart(Principal principal) {
        CartDTO cartDTO = cartService.getCartDTO(principal.getName());
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestParam Long productId,
                                             @RequestParam int quantity,
                                             Principal principal) {
        cartService.addProduct(principal.getName(), productId, quantity);
        return ResponseEntity.ok("Product added to cart");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProduct(@RequestParam Long productId, Principal principal) {
        cartService.removeProduct(principal.getName(), productId);
        return ResponseEntity.ok("Product removed from cart");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Principal principal) {
        cartService.clearCart(principal.getName());
        return ResponseEntity.ok("Cart cleared");
    }
}
