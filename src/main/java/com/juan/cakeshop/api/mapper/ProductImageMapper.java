package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.responses.ProductImageResponse;
import com.juan.cakeshop.api.dto.responses.ProductImageResponse;
import com.juan.cakeshop.api.model.ProductImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageMapper {
    public ProductImageResponse toResponse(ProductImage productImage)
    {
        return ProductImageResponse.builder()
                .imageId(productImage.getImageId())
                .imageUrl(productImage.getImageUrl())
                .isMain(productImage.getIsMain())
                .build();
    }


    public List<ProductImageResponse> toList(List<ProductImage> productImages)
    {
        return productImages.stream().map(this::toResponse).toList();
    }

}
