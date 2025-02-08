package com.lorenzozagallo.jpa.controllers;

import com.lorenzozagallo.jpa.dtos.OrderRecordDto;
import com.lorenzozagallo.jpa.models.Order;
import com.lorenzozagallo.jpa.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(id));
    }

    /*@PostMapping
    public ResponseEntity<Order> insertOrder(@RequestBody Order obj) {
        obj = orderService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }*/
    @PostMapping
    public ResponseEntity<Order> insertOrder(@RequestBody OrderRecordDto orderRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.insertOrder(orderRecordDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable UUID id, @RequestBody Order order) {

        return ResponseEntity.ok().body(orderService.updateOrder(id, order));
    }
}
