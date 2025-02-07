package com.lorenzozagallo.jpa.repositories;

import com.lorenzozagallo.jpa.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
