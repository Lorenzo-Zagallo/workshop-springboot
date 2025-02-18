package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.CategoryRecordDto;
import com.lorenzozagallo.jpa.models.Category;
import com.lorenzozagallo.jpa.models.User;
import com.lorenzozagallo.jpa.repositories.CategoryRepository;
import com.lorenzozagallo.jpa.services.exceptions.DatabaseException;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        logger.info("Buscando todos as categorias");
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        logger.info("Buscando categoria com ID: {}", id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + id));
    }

    public Category save(CategoryRecordDto categoryRecordDto) {
        logger.info("Salvando nova categoria: {}", categoryRecordDto.name());
        Category category = new Category();
        category.setName(categoryRecordDto.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Excluindo categoria com ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada para o ID: " + id);
        }
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro de integridade referencial ao excluir a categoria. MESSAGE:  " + e.getMessage());
        }
    }

    @Transactional
    public Category update(Long id, Category obj) {
        logger.info("Atualizando usuário com ID: {}", id);
        try {
            Category entity = categoryRepository.getReferenceById(id);
            updateData(entity, obj);
            return categoryRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Usuário não encontrado para o ID: " + id);
        }
    }

    private void updateData(Category entity, Category category) {
        Optional.ofNullable(category.getName()).ifPresent(entity::setName);
    }
}
