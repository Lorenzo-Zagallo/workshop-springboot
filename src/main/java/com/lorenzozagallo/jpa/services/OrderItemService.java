package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.models.Order;
import com.lorenzozagallo.jpa.models.OrderItem;
import com.lorenzozagallo.jpa.models.Product;
import com.lorenzozagallo.jpa.models.pk.OrderItemPK;
import com.lorenzozagallo.jpa.repositories.OrderItemRepository;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItemRepository getOrderItemRepository() {
        return orderItemRepository;
    }

    public OrderItem findByOrderAndProduct(Order order, Product product) {
        // cria o objeto da chave composta
        OrderItemPK id = new OrderItemPK(order, product);

        // busca o OrderItem com a chave composta
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do pedido não encontrado"));
    }

    @Transactional
    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
