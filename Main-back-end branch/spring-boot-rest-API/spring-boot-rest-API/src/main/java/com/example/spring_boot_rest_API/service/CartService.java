package com.example.spring_boot_rest_API.service;

import com.example.spring_boot_rest_API.dto.CartDTO;
import com.example.spring_boot_rest_API.dto.CartItemDTO;
import com.example.spring_boot_rest_API.dto.ProductDTO;
import com.example.spring_boot_rest_API.model.Cart;
import com.example.spring_boot_rest_API.model.CartItem;
import com.example.spring_boot_rest_API.model.Product;
import com.example.spring_boot_rest_API.model.User;
import com.example.spring_boot_rest_API.repository.CartItemRepository;
import com.example.spring_boot_rest_API.repository.CartRepository;
import com.example.spring_boot_rest_API.repository.ProductRepository;
import com.example.spring_boot_rest_API.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepo, CartItemRepository cartItemRepo,
                       UserRepository userRepo, ProductRepository productRepo) {
        this.cartRepository = cartRepo;
        this.cartItemRepository = cartItemRepo;
        this.userRepository = userRepo;
        this.productRepository = productRepo;
    }

    private Cart getOrCreateCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    public CartDTO getCartDTO(String username) {
        Cart cart = getOrCreateCart(username);

        List<CartItemDTO> itemDTOs = cart.getItems().stream().map(item -> {
            CartItemDTO dto = new CartItemDTO();

            // Create ProductDTO from Product entity
            Product product = item.getProduct();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());

            dto.setProduct(productDTO);
            dto.setQuantity(item.getQuantity());
            return dto;
        }).collect(Collectors.toList());

        CartDTO dto = new CartDTO();
        dto.setCartId(cart.getId());
        dto.setItems(itemDTOs);
        return dto;
    }

    public void addProduct(String username, Long productId, int quantity) {
        Cart cart = getOrCreateCart(username);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartAndProduct(cart, product);
        CartItem item;
        if (existingItemOpt.isPresent()) {
            item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
        }

        cartItemRepository.save(item);
    }

    public void removeProduct(String username, Long productId) {
        Cart cart = getOrCreateCart(username);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cartItemRepository.findByCartAndProduct(cart, product)
                .ifPresent(cartItemRepository::delete);
    }

    public void clearCart(String username) {
        Cart cart = getOrCreateCart(username);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
    public void updateProductQuantity(String username, Long productId, int quantity) {
        if (quantity <= 0) {
            // If quantity is zero or negative, remove the product from cart
            removeProduct(username, productId);
            return;
        }

        Cart cart = getOrCreateCart(username);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        } else {
            // Optionally add the product if not found (or throw error)
            throw new RuntimeException("Product not found in cart");
        }
    }

}
