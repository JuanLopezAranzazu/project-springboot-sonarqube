package com.juanlopezaranzazu.demo.application.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre del producto debe tener entre 3 y 50 caracteres")
    private String name;

    @NotBlank(message = "La descripción del producto no puede estar vacía")
    @Size(min = 10, max = 200, message = "La descripción del producto debe tener entre 10 y 200 caracteres")
    private String description;

    @NotNull(message = "El precio no puede ser nulo")
    @PositiveOrZero(message = "El precio debe ser mayor o igual a 0")
    private BigDecimal price;

    @NotNull(message = "El stock no puede ser nulo")
    @PositiveOrZero(message = "El stock debe ser mayor o igual a 0")
    private Integer stock;
}
