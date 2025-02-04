package com.lorenzozagallo.course.repositories;

import com.lorenzozagallo.course.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
