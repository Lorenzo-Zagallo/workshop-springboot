package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.ProductRecordDto;
import com.lorenzozagallo.jpa.models.Product;
import com.lorenzozagallo.jpa.repositories.ProductRepository;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product insertProduct(ProductRecordDto productRecordDto) {
        Product product = new Product();
        product.setName(productRecordDto.name());
        product.setDescription(productRecordDto.description());
        product.setPrice(productRecordDto.price());
        product.setImgUrl(productRecordDto.imgUrl());
        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product updateProduct(UUID id, Product product) {
        Product entity = productRepository.getReferenceById(id);
//        updateData(entity, product);
        Optional.ofNullable(product.getName()).ifPresent(entity::setName);
        Optional.ofNullable(product.getDescription()).ifPresent(entity::setDescription);
        Optional.ofNullable(product.getPrice()).ifPresent(entity::setPrice);;
        Optional.ofNullable(product.getImgUrl()).ifPresent(entity::setImgUrl);
        return productRepository.save(entity);
    }
    /*
    Optional.ofNullable: verifica se o valor de um campo é null.
    se não for null, o método ifPresent é chamado, e o valor é
    atribuído ao campo correspondente no objeto entity
    */

    /*public void updateData(Product entity, Product product) {
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setImgUrl(product.getImgUrl());
    }*/
}
