package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.CategoryRecordDto;
import com.lorenzozagallo.jpa.models.Category;
import com.lorenzozagallo.jpa.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository repository) {
        this.categoryRepository = repository;
    }


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(UUID id) {
        Optional<Category> obj = categoryRepository.findById(id);
        return obj.get();
    }

    public Category insertCategory(CategoryRecordDto categoryRecordDto) {
        Category category = new Category();
        category.setName(categoryRecordDto.name());
        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category updateCategory(UUID id, Category category) {
        Category entity = categoryRepository.getReferenceById(id);
        Optional.ofNullable(category.getName()).ifPresent(entity::setName);
        return categoryRepository.save(entity);
    }
}
