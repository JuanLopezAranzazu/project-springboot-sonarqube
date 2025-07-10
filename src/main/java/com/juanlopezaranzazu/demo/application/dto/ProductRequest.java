package com.juanlopezaranzazu.demo.application.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO para la solicitud de creación o actualización de un producto")
public class ProductRequest {
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre del producto", example = "Teclado mecánico RGB")
    private String name;

    @NotBlank(message = "La descripción del producto no puede estar vacía")
    @Size(min = 10, max = 200, message = "La descripción del producto debe tener entre 10 y 200 caracteres")
    @Schema(description = "Descripción del producto", example = "Teclado mecánico con retroiluminación RGB y teclas programables")
    private String description;

    @NotNull(message = "El precio no puede ser nulo")
    @PositiveOrZero(message = "El precio debe ser mayor o igual a 0")
    @Schema(description = "Precio del producto", example = "19.99")
    private BigDecimal price;

    @NotNull(message = "El stock no puede ser nulo")
    @PositiveOrZero(message = "El stock debe ser mayor o igual a 0")
    @Schema(description = "Cantidad de producto disponible en stock", example = "100")
    private Integer stock;
}
