package com.example.spring_boot_rest_API.controller;

import com.example.spring_boot_rest_API.service.NotificationService;
import com.example.spring_boot_rest_API.service.ProductService;
import com.example.spring_boot_rest_API.dto.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "http://localhost:5173") //temporary CORS fix (Just for testing)
@RestController //Postman: http://localhost:8080/api/products
@RequestMapping("/api")
public class ProductController
{
    private final ProductService productService;
    private final NotificationService notificationService;

    public ProductController(ProductService productService, NotificationService notificationService) {
        this.productService = productService;
        this.notificationService = notificationService;
    }


    @PostMapping("/products")
    public CompletableFuture<ResponseEntity<ProductDTO>> saveProduct(@Valid @RequestBody ProductDTO productDto) {
        return CompletableFuture.supplyAsync(() -> {
            ProductDTO saved = productService.saveProduct(productDto);  // DB save
            notificationService.notifyProduct(saved);                  // fire-and-forget async
            return ResponseEntity.ok(saved);
        });
    }


    @GetMapping("/products")
    public List<ProductDTO> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) //PathVariable takes the value from the URL
    {
        Optional<ProductDTO> optional = productService.getProductById(id);
        if (optional.isPresent()) //could shorten but consider this more readable.
        {
            return ResponseEntity.ok(optional.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDTO> deleteProductById(@PathVariable Long id)
    {
        Optional<ProductDTO> optional = productService.getProductById(id);
        if(optional.isPresent())
        {
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();//Standard success response for delete requests
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
        // Check if the product exists
        Optional<ProductDTO> existingProduct = productService.getProductById(id);

        if (existingProduct.isPresent()) {
            // If the product exists, update it
            ProductDTO updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct); // Return updated product with status 200 OK
        } else {
            // If the product doesn't exist, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

}
