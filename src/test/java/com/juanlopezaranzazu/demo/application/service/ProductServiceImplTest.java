package com.juanlopezaranzazu.demo.application.service;

import com.juanlopezaranzazu.demo.application.dto.ProductRequest;
import com.juanlopezaranzazu.demo.application.dto.ProductResponse;
import com.juanlopezaranzazu.demo.domain.model.Product;
import com.juanlopezaranzazu.demo.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("10.99"));
        product.setStock(5);

        productRequest = new ProductRequest();
        productRequest.setName("Updated Product");
        productRequest.setDescription("Updated Description");
        productRequest.setPrice(new BigDecimal("15.00"));
        productRequest.setStock(10);
    }

    @Test
    void testGetAllProducts_success() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponse> responses = productService.getAllProducts();

        assertEquals(1, responses.size());
        assertEquals("Test Product", responses.get(0).getName());
        verify(productRepository).findAll();
    }

    @Test
    void testGetAllProducts_empty() {
        when(productRepository.findAll()).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productService.getAllProducts());
        assertEquals("No hay productos disponibles", ex.getMessage());
    }

    @Test
    void testGetProductById_found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertEquals("Test Product", response.getName());
    }

    @Test
    void testGetProductById_notFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
        assertEquals("El producto no existe con el id: 1", ex.getMessage());
    }

    @Test
    void testCreateProduct_success() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(productRequest);

        assertEquals("Test Product", response.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.updateProduct(1L, productRequest);

        assertEquals("Updated Product", response.getName());
    }

    @Test
    void testUpdateProduct_notFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productService.updateProduct(1L, productRequest));
        assertEquals("El producto no existe con el id: 1", ex.getMessage());
    }

    @Test
    void testDeleteProduct_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1L);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository).deleteById(1L);
    }

    @Test
    void testDeleteProduct_notFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productService.deleteProduct(1L));
        assertEquals("El producto no existe con el id: 1", ex.getMessage());
    }
}
