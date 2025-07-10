package com.juanlopezaranzazu.demo.application.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.juanlopezaranzazu.demo.application.dto.ProductRequest;
import com.juanlopezaranzazu.demo.application.dto.ProductResponse;
import com.juanlopezaranzazu.demo.domain.model.Product;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

    public ProductResponse toProductResponse(Product product) {
        if(product == null) {
            return null;
        }
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock(),
            product.getCreatedAt()
        );
    }

    public Product toProduct(ProductRequest productRequest) {
        if(productRequest == null) {
            return null;
        }
        return new Product(
            productRequest.getName(),
            productRequest.getDescription(),
            productRequest.getPrice(),
            productRequest.getStock()
        );
    }

    public void updateProductFromRequest(Product product, ProductRequest productRequest) {
        if (productRequest == null || product == null) return;

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
    }

    public List<ProductResponse> toProductResponseList(List<Product> products) {
        if(products == null) {
            return List.of();
        }
        return products.stream()
            .map(ProductMapper::toProductResponse)
            .collect(Collectors.toList());
    }
}
