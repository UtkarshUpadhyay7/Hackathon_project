package com.expirytracker.controller;

import com.expirytracker.model.Product;
import com.expirytracker.repository.ProductRepository;
import com.expirytracker.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Allows requests from any frontend
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AlertService alertService;

    // ✅ 1. Add Product - Used for both Manual & QR Upload (Backend logic is the same)
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            // Save to database
            Product savedProduct = productRepository.save(product);

            // Call Alert Service
            String alertMsg = alertService.checkSingleProductAlert(savedProduct);

            return ResponseEntity.ok(alertMsg != null ? alertMsg : savedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Failed to save product: " + e.getMessage());
        }
    }

    // ✅ 2. Add Product via QR specifically (optional route)
    @PostMapping("/uploadQR")
    public ResponseEntity<?> uploadProductFromQR(@RequestBody Product product) {
        try {
            Product savedProduct = productRepository.save(product);

            // Check expiry alert logic
            String alertMsg = alertService.checkSingleProductAlert(savedProduct);

            return ResponseEntity.ok(alertMsg != null ? alertMsg : savedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Failed to save QR product: " + e.getMessage());
        }
    }

    // ✅ 3. Get All Products
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    // ✅ 4. Get Product by Name (case-insensitive)
    @GetMapping("/{name}")
    public ResponseEntity<List<Product>> getByName(@PathVariable String name) {
        List<Product> products = productRepository.findByNameIgnoreCase(name);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    // ✅ 5. Delete Product by Name (case-insensitive)
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name) {
        List<Product> products = productRepository.findByNameIgnoreCase(name);
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ No products found with name: " + name);
        }
        productRepository.deleteAll(products);
        return ResponseEntity.ok("✅ Deleted all products with name: " + name);
    }
}
