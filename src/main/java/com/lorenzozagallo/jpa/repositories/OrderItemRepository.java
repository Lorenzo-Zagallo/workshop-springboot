package com.lorenzozagallo.jpa.repositories;

import com.lorenzozagallo.jpa.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
