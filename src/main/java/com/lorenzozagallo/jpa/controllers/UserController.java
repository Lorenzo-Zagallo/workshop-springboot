package com.lorenzozagallo.jpa.controllers;

import com.lorenzozagallo.jpa.dtos.UserRecordDto;
import com.lorenzozagallo.jpa.models.User;
import com.lorenzozagallo.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/workshop/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
       /* User u = new User(UUID.randomUUID(), "Maria", "maria@gmail.com", "9999999", "12345");
        return ResponseEntity.ok().body(u);
        List<User> list = userService.getAllUsers();
        return ResponseEntity.ok().body(list);*/
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        /*User obj = userService.findUserById(id);
        return ResponseEntity.ok().body(obj);*/
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }

    /*@PostMapping
    public ResponseEntity<User> insert(@RequestBody User obj) {
        obj = userService.insertUser(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }*/
    @PostMapping
    public ResponseEntity<User> insertUser(@RequestBody UserRecordDto userRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insertUser(userRecordDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(id, user));
    }
}
