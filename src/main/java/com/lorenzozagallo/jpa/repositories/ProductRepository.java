package com.lorenzozagallo.jpa.repositories;

import com.lorenzozagallo.jpa.models.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @EntityGraph(attributePaths = "categories") // força o carregamento de categories
    Optional<Product> findById(UUID id);
}
