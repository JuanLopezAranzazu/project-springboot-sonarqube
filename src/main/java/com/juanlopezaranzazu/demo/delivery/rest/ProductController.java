package com.juanlopezaranzazu.demo.delivery.rest;

import com.juanlopezaranzazu.demo.application.dto.ProductRequest;
import com.juanlopezaranzazu.demo.application.dto.ProductResponse;
import com.juanlopezaranzazu.demo.domain.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles.")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve los detalles de un producto específico por su ID.")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(productService.getProductById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Permite crear un nuevo producto en el sistema.")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(201).body(productService.createProduct(productRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Permite actualizar los detalles de un producto existente.")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(200).body(productService.updateProduct(id, productRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Permite eliminar un producto del sistema por su ID.")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(204).build();
    }
}
