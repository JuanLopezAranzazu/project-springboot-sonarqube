package com.juanlopezaranzazu.demo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la respuesta de un producto")
public class ProductResponse {
    @Schema(description = "ID del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Teclado mecánico RGB")
    private String name;

    @Schema(description = "Descripción del producto", example = "Teclado mecánico con retroiluminación RGB y teclas programables")
    private String description;

    @Schema(description = "Precio del producto", example = "19.99")
    private BigDecimal price;

    @Schema(description = "Cantidad de producto disponible en stock", example = "100")
    private Integer stock;

    @Schema(description = "Fecha de creación del producto", example = "2023-10-01T12:00:00")
    private LocalDateTime createdAt;
}
