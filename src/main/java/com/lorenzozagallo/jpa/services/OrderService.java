package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.models.Order;
import com.lorenzozagallo.jpa.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(UUID id) {
        Optional<Order> obj = repository.findById(id);
        return obj.get();
    }

    public Order insert(Order obj) {
        return repository.save(obj);
    }
}
