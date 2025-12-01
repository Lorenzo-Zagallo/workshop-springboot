package com.lorenzozagallo.jpa.controllers;

import com.lorenzozagallo.jpa.models.OrderItem;
import com.lorenzozagallo.jpa.models.pk.OrderItemPK;
import com.lorenzozagallo.jpa.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workshop/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItem>> findAll() {
        List<OrderItem> items = orderItemService.findAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderItem> findById(
            @PathVariable Long orderId, @PathVariable Long productId) {
        OrderItemPK id = new OrderItemPK(orderId, productId);
        return orderItemService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderItem> save(@RequestBody OrderItem orderItem) {
        orderItemService.save(orderItem);
        return ResponseEntity.ok(orderItem);
    }

    @DeleteMapping("/{orderId}/{productId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long orderId, @PathVariable Long productId) {
        OrderItemPK id = new OrderItemPK(orderId, productId);
        orderItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
