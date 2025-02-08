package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.OrderItemRecordDto;
import com.lorenzozagallo.jpa.dtos.OrderRecordDto;
import com.lorenzozagallo.jpa.models.*;
import com.lorenzozagallo.jpa.repositories.OrderRepository;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /*public Order getOrder(UUID id) {
        Optional<Order> obj = orderRepository.findById(id);
        return obj.get();
    }*/
    @Transactional // mantém a sessão aberta enquanto a consulta é processada
    public Order getOrder(UUID id) {
        Order order = orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return order;
    }

    public Order insertOrder(OrderRecordDto orderRecordDto) {
        User user = userService.findUserById(orderRecordDto.userID());
        Order order = new Order(null, orderRecordDto.moment(),
                orderRecordDto.getOrderStatus(), user);

        // converte os itens do dto para entidades OrderItem
        List<OrderItem> orderItems = orderRecordDto.items().stream()
                .map(dto -> {
                    Product product = productService.getProduct(dto.productID()); // obtém o produto pelo ID
                    return new OrderItem(order, product, dto.quantity(), dto.price());
                })
                .toList();
        order.getItems().addAll(orderItems); // adiciona os items no pedido

        return orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order updateOrder(UUID id, Order order) {
        Order entity = orderRepository.getReferenceById(id);
        Optional.ofNullable(order.getMoment()).ifPresent(entity::setMoment);
        Optional.ofNullable(order.getOrderStatus()).ifPresent(entity::setOrderStatus);
        Optional.ofNullable(order.getClient()).ifPresent(entity::setClient);

        return orderRepository.save(entity);
    }

    public Order addItemToOrder(UUID orderId, OrderItemRecordDto orderItemRecordDto) {
        // busca o pedido no banco de dados
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        // busca o produto pelo ID
        Product product = productService.getProduct(orderItemRecordDto.productID());

        // cria um novo orderItem
        OrderItem newItem = new OrderItem(order, product,
                orderItemRecordDto.quantity(), orderItemRecordDto.price());

        order.getItems().add(newItem); // adiciona o item ao pedido

        return orderRepository.save(order); // salva e retorna o pedido atualizado
    }
}
