package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.CategoryRecordDto;
import com.lorenzozagallo.jpa.models.Category;
import com.lorenzozagallo.jpa.repositories.CategoryRepository;
import com.lorenzozagallo.jpa.services.exceptions.DatabaseException;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CategoryService {

    private static final Logger LOGGER = Logger.getLogger(CategoryService.class.getName());

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        LOGGER.info("Buscando todas as categorias");
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        LOGGER.info("Buscando categoria com ID: " + id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> { 
                    LOGGER.warning("Categoria não encontrada para o ID: " + id);
                    return new ResourceNotFoundException("Categoria não encontrada para o ID: " + id);
                });
    }

    public Category save(CategoryRecordDto categoryRecordDto) {
        LOGGER.info("Salvando nova categoria: " + categoryRecordDto.name());
        Category category = new Category();
        category.setName(categoryRecordDto.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Excluindo categoria com ID: " + id);
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada para o ID: " + id);
        }
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir categoria que possui produtos.");
        }
    }

    @Transactional
    public Category update(Long id, CategoryRecordDto categoryRecordDto) {
        LOGGER.info("Atualizando categoria com ID: " + id);
        Category entity = findById(id);
        entity.setName(categoryRecordDto.name());
        return categoryRepository.save(entity);
    }
}