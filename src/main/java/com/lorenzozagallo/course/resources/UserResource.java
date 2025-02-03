package com.lorenzozagallo.course.resources;

import com.lorenzozagallo.course.entities.User;
import com.lorenzozagallo.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    // requisição do tipo GET
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
//        User u = new User(UUID.randomUUID(), "Maria", "maria@gmail.com", "9999999", "12345");
//        return ResponseEntity.ok().body(u);
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
}
