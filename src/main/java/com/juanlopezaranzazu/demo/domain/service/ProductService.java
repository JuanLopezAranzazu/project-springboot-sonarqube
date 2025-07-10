package com.juanlopezaranzazu.demo.domain.service;

import com.juanlopezaranzazu.demo.application.dto.ProductRequest;
import com.juanlopezaranzazu.demo.application.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
}
