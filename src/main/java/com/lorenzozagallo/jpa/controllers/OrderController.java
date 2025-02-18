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
import java.util.Optional;

@RestController
@RequestMapping(value = "/workshop/orders")
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
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Order>> findById(@PathVariable Long id) {
        Optional<Order> order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }

    /*@PostMapping
    public ResponseEntity<Order> insertOrder(@RequestBody Order obj) {
        obj = orderService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }*/
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
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order) {
        Order order1 = orderService.update(id, order);
        return ResponseEntity.ok().body(order1);
    }

    @PutMapping("/{orderId}/items")
    public ResponseEntity<OrderItem> addItemToOrder(@PathVariable Long orderId,
                                                    @RequestBody OrderItemRecordDto orderItemRecordDto) {
        // buscar o pedido
        Optional<Order> order = orderService.findById(orderId);
        if (order.isEmpty()) {
            // retorna erro 404 caso o pedido não seja encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // buscar o produto
        Optional<Product> product = productService.findById(orderItemRecordDto.productID());
        if (product.isEmpty()) {
            // retorna erro 404 caso o produto não seja encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        OrderItem item = new OrderItem();
        item.setOrder(order.get());
        item.setProduct(product.get());
        item.setQuantity(orderItemRecordDto.quantity());
        item.setPrice(orderItemRecordDto.price()); // aqui o preço será setado corretamente

        orderItemService.save(item); // salvar o item no pedido
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

}
