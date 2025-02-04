package com.lorenzozagallo.course.repositories;

import com.lorenzozagallo.course.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
