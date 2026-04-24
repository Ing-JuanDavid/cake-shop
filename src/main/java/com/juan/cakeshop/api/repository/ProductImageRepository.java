package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findAllByProductProductId(int productId);
}
