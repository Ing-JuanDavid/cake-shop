package com.juan.cakeshop.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer imageId;
    String imageUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
