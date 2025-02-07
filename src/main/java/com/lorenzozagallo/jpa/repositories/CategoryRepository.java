package com.lorenzozagallo.jpa.repositories;

import com.lorenzozagallo.jpa.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
