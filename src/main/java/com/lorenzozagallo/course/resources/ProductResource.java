package com.lorenzozagallo.course.resources;

import com.lorenzozagallo.course.entities.Product;
import com.lorenzozagallo.course.entities.User;
import com.lorenzozagallo.course.services.ProductService;
import com.lorenzozagallo.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService service;

    // requisição do tipo GET
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
//        User u = new User(UUID.randomUUID(), "Maria", "maria@gmail.com", "9999999", "12345");
//        return ResponseEntity.ok().body(u);
        List<Product> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
}
