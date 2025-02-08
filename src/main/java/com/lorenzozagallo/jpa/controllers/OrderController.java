package com.lorenzozagallo.jpa.controllers;

import com.lorenzozagallo.jpa.dtos.OrderItemRecordDto;
import com.lorenzozagallo.jpa.dtos.OrderRecordDto;
import com.lorenzozagallo.jpa.models.Order;
import com.lorenzozagallo.jpa.models.OrderItem;
import com.lorenzozagallo.jpa.models.Product;
import com.lorenzozagallo.jpa.services.OrderItemService;
import com.lorenzozagallo.jpa.services.OrderService;
import com.lorenzozagallo.jpa.services.ProductService;
import com.lorenzozagallo.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemService orderItemService;

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

    @PutMapping("/{orderId}/items")
    public ResponseEntity<OrderItem> addItemToOrder(@PathVariable UUID orderId,
                                                    @RequestBody OrderItemRecordDto orderItemRecordDto) {
        // buscar o pedido
        Order order = orderService.getOrder(orderId);

        // buscar o produto
        Product product = productService.getProduct(orderItemRecordDto.productID());

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(orderItemRecordDto.quantity());
        item.setPrice(orderItemRecordDto.price()); // aqui o preço será setado corretamente

        orderItemService.save(item); // salvar o item no pedido
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

}
