package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.OrderItemRecordDto;
import com.lorenzozagallo.jpa.dtos.OrderRecordDto;
import com.lorenzozagallo.jpa.models.*;
import com.lorenzozagallo.jpa.repositories.OrderRepository;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;


    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order save(OrderRecordDto orderRecordDto) {
        try {
            User user = userService.findById(orderRecordDto.userID());

            Order order = new Order(null,
                    orderRecordDto.moment(),
                    orderRecordDto.getOrderStatus(),
                    user);

            List<OrderItem> orderItems = orderRecordDto.items().stream()
                    .map(dto -> {
                        Product product = productService.findById(dto.productID())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        "Produto não encontrado para o ID: " + dto.productID()));
                        return new OrderItem(Optional.of(order), Optional.of(product), dto.quantity(), dto.price());
                    })
                    .toList();

            order.getItems().addAll(orderItems);

            return orderRepository.save(order);

        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Usuário não encontrado para o ID: " + orderRecordDto.userID());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao salvar o pedido: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado para o ID: " + id);
        }
        try {
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao excluir o pedido: " + e.getMessage());
        }
    }

    @Transactional
    public Order update(Long id, Order order) {
        Order entity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado para o ID: " + id));

        Optional.ofNullable(order.getMoment()).ifPresent(entity::setMoment);
        Optional.ofNullable(order.getOrderStatus()).ifPresent(entity::setOrderStatus);
        Optional.ofNullable(order.getClient()).ifPresent(entity::setClient);

        try {
            return orderRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao atualizar o pedido: " + e.getMessage());
        }
    }

    @Transactional
    public Order addItemToOrder(Long orderId, OrderItemRecordDto orderItemRecordDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado para o ID: " + orderId));

        Product product = productService.findById(orderItemRecordDto.productID())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para o ID: " + orderItemRecordDto.productID()));

        OrderItem newItem = new OrderItem(Optional.of(order), Optional.of(product),
                orderItemRecordDto.quantity(), orderItemRecordDto.price());

        order.getItems().add(newItem);

        try {
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao adicionar item ao pedido: " + e.getMessage());
        }
    }
}
