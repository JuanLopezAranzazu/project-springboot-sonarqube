package com.juanlopezaranzazu.demo.infrastructure.persistence;

import com.juanlopezaranzazu.demo.domain.model.Product;
import com.juanlopezaranzazu.demo.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryImpl extends JpaRepository<Product, Long>, ProductRepository {

}
