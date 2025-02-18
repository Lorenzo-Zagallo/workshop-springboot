package com.lorenzozagallo.jpa.repositories;

import com.lorenzozagallo.jpa.models.OrderItem;
import com.lorenzozagallo.jpa.models.pk.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
