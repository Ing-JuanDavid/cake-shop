package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.dto.responses.ProductSimpleResponse;
import com.juan.cakeshop.api.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @Query("""
            SELECT new com.juan.cakeshop.api.dto.responses.ProductSimpleResponse(
                    p.productId,
                    p.name,
                    p.price,
                    c.name,
                    new com.juan.cakeshop.api.dto.responses.ProductImageResponse(i.imageId, i.imageUrl, i.isMain)
                )
                FROM Product p
                JOIN p.category c
                LEFT JOIN p.productImages i ON  i.isMain = true
                WHERE p.category.categoryId = :categoryId
                AND p.isActive = true
            """)
    List<ProductSimpleResponse> findActiveProductsByCategory(@Param("categoryId") int categoryId);

    @EntityGraph(attributePaths = {"category","productImages"})
    Optional<Product> findByProductIdAndIsActiveTrue(int productId);
}
