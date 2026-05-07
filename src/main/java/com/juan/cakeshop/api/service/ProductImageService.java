package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.responses.ProductImageResponse;

public interface ProductImageService {
    ProductImageResponse updateProductImage(Boolean isMain, int productImageId);
}
