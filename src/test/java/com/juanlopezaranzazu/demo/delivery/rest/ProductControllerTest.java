package com.juanlopezaranzazu.demo.delivery.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanlopezaranzazu.demo.application.dto.ProductRequest;
import com.juanlopezaranzazu.demo.application.dto.ProductResponse;
import com.juanlopezaranzazu.demo.domain.service.ProductService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private final ProductResponse mockResponse = new ProductResponse(
        1L,
        "Test Product",
        "Test Description",
        new BigDecimal("10.00"),
        5,
        LocalDateTime.now()
    );

    @Test
    void shouldReturnAllProducts() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        Mockito.when(productService.getProductById(1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest("Test Product", "Test Description", new BigDecimal("10.00"), 5);
        Mockito.when(productService.createProduct(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductRequest request = new ProductRequest("Updated", "Updated desc", new BigDecimal("15.00"), 10);
        Mockito.when(productService.updateProduct(any(), any())).thenReturn(mockResponse);

        mockMvc.perform(put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/products/1"))
            .andExpect(status().isNoContent());
    }
}
