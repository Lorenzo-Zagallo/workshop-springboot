package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.models.OrderItem;
import com.lorenzozagallo.jpa.models.pk.OrderItemPK;
import com.lorenzozagallo.jpa.repositories.OrderItemRepository;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OrderItemService {

    private static final Logger LOGGER = Logger.getLogger(OrderItemService.class.getName());

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> findAll() {
        LOGGER.info("Buscando todos os itens do pedido");
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> findById(OrderItemPK id) {
        LOGGER.info("Buscando item do pedido com ID: " + id);
        return orderItemRepository.findById(id);
    }

    public OrderItem findByOrderAndProduct(Long order, Long product) {
        LOGGER.info("Buscando item do pedido para Order ID: " + order + " e Product ID: " + product);
        OrderItemPK id = new OrderItemPK(order, product);
        return orderItemRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warning("Item do pedido não encontrado para o ID: " + id);
                    return new ResourceNotFoundException("Item do pedido não encontrado para o ID: " + id);
                });
    }

    @Transactional
    public OrderItem save(OrderItem orderItem) {
        LOGGER.info("Salvando item do pedido");
        try {
            return orderItemRepository.save(orderItem);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao salvar o item do pedido: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteById(OrderItemPK id) {
        LOGGER.info("Excluindo item do pedido com ID: " + id);
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