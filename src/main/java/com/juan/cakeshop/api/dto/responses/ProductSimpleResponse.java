package com.juan.cakeshop.api.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSimpleResponse {
    private Integer  productId;
    private String name;
    private Integer price;
    private String categoryName;
    private ProductImageResponse mainImage;
}
