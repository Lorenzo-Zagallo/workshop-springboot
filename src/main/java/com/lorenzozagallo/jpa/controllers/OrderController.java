package com.lorenzozagallo.jpa.controllers;

import com.lorenzozagallo.jpa.dtos.OrderItemRecordDto;
import com.lorenzozagallo.jpa.dtos.OrderRecordDto;
import com.lorenzozagallo.jpa.models.Order;
import com.lorenzozagallo.jpa.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping(value = "/workshop/orders")
public class OrderController {

    private final OrderService orderService;

    // Injeção via Construtor
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        // Service retorna o objeto direto ou lança exceção (tratada no
        // GlobalExceptionHandler)
        Order order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody OrderRecordDto orderRecordDto) {
        Order order = orderService.save(orderRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody OrderRecordDto dto) {
        Order order = orderService.update(id, dto);
        return ResponseEntity.ok().body(order);
    }

    // Endpoint específico para adicionar item (Clean Code: delegando para o
    // Service)
    @PostMapping("/{orderId}/items")
    public ResponseEntity<Order> addItemToOrder(@PathVariable Long orderId,
            @RequestBody OrderItemRecordDto orderItemRecordDto) {
        Order updatedOrder = orderService.addItemToOrder(orderId, orderItemRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedOrder);
    }
}