package com.lorenzozagallo.jpa.controllers;

import com.lorenzozagallo.jpa.dtos.ProductRecordDto;
import com.lorenzozagallo.jpa.models.Product;
import com.lorenzozagallo.jpa.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/workshop/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // requisição do tipo GET
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
//        User u = new User(UUID.randomUUID(), "Maria", "maria@gmail.com", "9999999", "12345");
//        return ResponseEntity.ok().body(u);
        List<Product> list = productService.getAllProducts();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID id) {
        Product obj = productService.getProduct(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Product> insertProduct(@RequestBody ProductRecordDto productRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.insertProduct(productRecordDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateUser(@PathVariable UUID id, @RequestBody Product product) {
        return ResponseEntity.ok().body(productService.updateProduct(id, product));
    }
}
