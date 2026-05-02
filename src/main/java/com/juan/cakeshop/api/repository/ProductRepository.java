package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByCategoryCategoryIdAndIsActiveTrue(int categoryId);
    Optional<Product> findByProductIdAndIsActiveTrue(int productId);
}
