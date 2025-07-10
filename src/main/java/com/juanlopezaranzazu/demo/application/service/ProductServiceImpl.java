package com.juanlopezaranzazu.demo.application.service;

import com.juanlopezaranzazu.demo.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
}
