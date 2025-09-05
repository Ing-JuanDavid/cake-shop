package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
