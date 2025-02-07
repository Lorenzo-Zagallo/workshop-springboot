package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.ProductRecordDto;
import com.lorenzozagallo.jpa.models.Product;
import com.lorenzozagallo.jpa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(UUID id) {
        Optional<Product> obj = productRepository.findById(id);
        return obj.get();
    }

    public Product saveProduct(ProductRecordDto productRecordDto) {
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
}
