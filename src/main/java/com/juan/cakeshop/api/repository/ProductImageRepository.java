package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findAllByProductProductId(int productId);

    @Transactional
    @Modifying
    @Query("UPDATE ProductImage pi SET pi.isMain = false WHERE pi.product.productId = :productId AND pi.imageId != :imageId")
    void  clearMainProductImage(@Param("productId") int productId, @Param("imageId") int imageId);
}
