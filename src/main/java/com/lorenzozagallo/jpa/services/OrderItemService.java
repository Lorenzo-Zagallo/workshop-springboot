package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.models.Order;
import com.lorenzozagallo.jpa.models.OrderItem;
import com.lorenzozagallo.jpa.models.Product;
import com.lorenzozagallo.jpa.models.pk.OrderItemPK;
import com.lorenzozagallo.jpa.repositories.OrderItemRepository;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> findById(OrderItemPK id) {
        return orderItemRepository.findById(id);
    }

    public OrderItem findByOrderAndProduct(Long order, Long product) {
        OrderItemPK id = new OrderItemPK(order, product);
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do pedido não encontrado."));
    }

    @Transactional
    public OrderItem save(OrderItem orderItem) {
        try {
            return orderItemRepository.save(orderItem);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao salvar o item do pedido: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteById(OrderItemPK id) {
        if (!orderItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item do pedido não encontrado para exclusão.");
        }
        try {
            orderItemRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao excluir o item do pedido: " + e.getMessage());
        }
    }
}