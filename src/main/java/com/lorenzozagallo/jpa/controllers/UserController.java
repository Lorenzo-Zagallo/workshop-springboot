package com.lorenzozagallo.jpa.controllers;

import com.lorenzozagallo.jpa.dtos.UserRecordDto;
import com.lorenzozagallo.jpa.models.User;
import com.lorenzozagallo.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/workshop/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
       /* User u = new User(UUID.randomUUID(), "Maria", "maria@gmail.com", "9999999", "12345");
        return ResponseEntity.ok().body(u);*/
        List<User> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable Long id) {
        Optional<User> obj = userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    /*@PostMapping
    public ResponseEntity<User> insert(@RequestBody User obj) {
        obj = userService.insertUser(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }*/
    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserRecordDto userRecordDto) {
        User obj = userService.save(userRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        User obj = userService.update(id, user);
        return ResponseEntity.ok().body(obj);
    }
}
