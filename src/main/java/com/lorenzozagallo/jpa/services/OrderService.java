package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.OrderRecordDto;
import com.lorenzozagallo.jpa.models.Category;
import com.lorenzozagallo.jpa.models.Order;
import com.lorenzozagallo.jpa.models.enums.OrderStatus;
import com.lorenzozagallo.jpa.repositories.OrderRepository;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

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
        Order order = new Order();
        order.setMoment(orderRecordDto.moment());
        order.setOrderStatus(orderRecordDto.orderStatus());
        order.setClient(orderRecordDto.user());
        return orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order updateOrder(UUID id, Order order) {
        Order entity = orderRepository.getReferenceById(id);
        entity.setMoment(order.getMoment());
        entity.setOrderStatus(order.getOrderStatus());
        entity.setClient(order.getClient());
        return orderRepository.save(entity);
    }
}
