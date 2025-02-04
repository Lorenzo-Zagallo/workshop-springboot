package com.lorenzozagallo.course.repositories;

import com.lorenzozagallo.course.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
