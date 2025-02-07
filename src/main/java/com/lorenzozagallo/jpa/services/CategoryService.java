package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.models.Category;
import com.lorenzozagallo.jpa.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(UUID id) {
        Optional<Category> obj = repository.findById(id);
        return obj.get();
    }
}
