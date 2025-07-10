package com.juanlopezaranzazu.demo.application.service;

import com.juanlopezaranzazu.demo.application.dto.ProductRequest;
import com.juanlopezaranzazu.demo.application.dto.ProductResponse;
import com.juanlopezaranzazu.demo.application.mapper.ProductMapper;
import com.juanlopezaranzazu.demo.domain.model.Product;
import com.juanlopezaranzazu.demo.domain.repository.ProductRepository;
import com.juanlopezaranzazu.demo.domain.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new RuntimeException("No hay productos disponibles");
        }
        return ProductMapper.toProductResponseList(products);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new RuntimeException("El producto no existe con el id: " + id);
        }
        return ProductMapper.toProductResponse(product.get());
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.toProduct(productRequest);
        if (product == null) {
            throw new RuntimeException("La solicitud del producto es inválida");
        }
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("El producto no existe con el id: " + id);
        }
        Product product = existingProduct.get();
        ProductMapper.updateProductFromRequest(product, productRequest);
        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("El producto no existe con el id: " + id);
        }
        productRepository.deleteById(id);
    }
}
