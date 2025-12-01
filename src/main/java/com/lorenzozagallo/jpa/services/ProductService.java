package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.ProductRecordDto;
import com.lorenzozagallo.jpa.models.Product;
import com.lorenzozagallo.jpa.repositories.ProductRepository;
import com.lorenzozagallo.jpa.services.exceptions.DatabaseException;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ProductService {

    private static final Logger LOGGER = Logger.getLogger(ProductService.class.getName());

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        LOGGER.info("Buscando produto com ID: " + id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warning("Produto não encontrado para o ID: " + id);
                    return new ResourceNotFoundException("Produto não encontrado para o ID: " + id);
                });
    }

    @Transactional
    public Product save(ProductRecordDto productRecordDto) {
        LOGGER.info("Salvando novo produto: " + productRecordDto.name());
        Product product = new Product();
        product.setName(productRecordDto.name());
        product.setDescription(productRecordDto.description());
        product.setPrice(productRecordDto.price());
        product.setImgUrl(productRecordDto.imgUrl());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        LOGGER.info("Excluindo produto com ID: " + id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado para o ID: " + id);
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir usuário que possui pedidos relacionados.");
        }
    }

    @Transactional
    public Product update(Long id, ProductRecordDto productDto) {
        LOGGER.info("Atualizando produto com ID: " + id);
        Product entity = findById(id);
        updateData(entity, productDto);
        return productRepository.save(entity);
    }

    private void updateData(Product entity, ProductRecordDto dto) {
        if (dto.name() != null)
            entity.setName(dto.name());
        if (dto.description() != null)
            entity.setDescription(dto.description());
        if (dto.price() != null)
            entity.setPrice(dto.price());
        if (dto.imgUrl() != null)
            entity.setImgUrl(dto.imgUrl());
    }
}