package com.juanlopezaranzazu.demo.delivery.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanlopezaranzazu.demo.application.dto.ProductRequest;
import com.juanlopezaranzazu.demo.delivery.rest.ProductController;
import com.juanlopezaranzazu.demo.domain.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldHandleValidationException() throws Exception {
        ProductRequest invalidRequest = new ProductRequest(
                "",  // name vacío
                "short", // descripción corta
                BigDecimal.TEN,
                10
        );

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.name").exists())
                .andExpect(jsonPath("$.errors.description").exists());
    }

    @Test
    void shouldHandleGeneralException() throws Exception {
        Mockito.when(productService.createProduct(any()))
                .thenThrow(new RuntimeException("Algo salió mal"));

        ProductRequest validRequest = new ProductRequest(
                "Producto",
                "Descripción válida",
                BigDecimal.TEN,
                10
        );

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Error inesperado: Algo salió mal"))
                .andExpect(jsonPath("$.status").value(500));
    }

    @Test
    void shouldHandleDataAccessException() throws Exception {
        DataAccessException dbException = new DataAccessException("Error en la base de datos") {
            @Override
            public Throwable getMostSpecificCause() {
                return new RuntimeException("Detalle del error SQL");
            }
        };

        Mockito.when(productService.createProduct(any()))
                .thenThrow(dbException);

        ProductRequest validRequest = new ProductRequest(
                "Producto",
                "Descripción válida",
                BigDecimal.TEN,
                10
        );

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Error de base datos: Error en la base de datos: Detalle del error SQL"))
                .andExpect(jsonPath("$.status").value(500));
    }
}
