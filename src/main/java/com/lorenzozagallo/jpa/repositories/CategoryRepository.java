package com.lorenzozagallo.jpa.repositories;

import com.lorenzozagallo.jpa.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
