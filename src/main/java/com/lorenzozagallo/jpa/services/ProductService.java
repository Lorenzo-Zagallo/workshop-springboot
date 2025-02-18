package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.ProductRecordDto;
import com.lorenzozagallo.jpa.models.Product;
import com.lorenzozagallo.jpa.repositories.ProductRepository;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id)
                .or(() -> {
                    LOGGER.warning("Produto não encontrado para o ID: " + id);
                    throw new ResourceNotFoundException("Produto não encontrado para o ID: " + id);
                });
    }

    public Product save(ProductRecordDto productRecordDto) {
        Product product = new Product();
        product.setName(productRecordDto.name());
        product.setDescription(productRecordDto.description());
        product.setPrice(productRecordDto.price());
        product.setImgUrl(productRecordDto.imgUrl());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado para o ID: " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public Product update(Long id, Product product) {
        Product entity = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Produto não encontrado para o ID: " + id));
        updateData(entity, product);
        return productRepository.save(entity);
    }

    private void updateData(Product entity, Product product) {
        Optional.ofNullable(product.getName()).ifPresent(entity::setName);
        Optional.ofNullable(product.getDescription()).ifPresent(entity::setDescription);
        Optional.ofNullable(product.getPrice()).ifPresent(entity::setPrice);
        Optional.ofNullable(product.getImgUrl()).ifPresent(entity::setImgUrl);
    }
}
