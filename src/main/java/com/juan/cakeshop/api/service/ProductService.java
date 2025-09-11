package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.responses.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductDto productDto);

    List<ProductResponse> getAllProducts();

    ProductResponse deleteProduct(int productId);

    ProductResponse updateProduct(int productId, ProductDto productDto);

    ProductResponse getProduct(int productId);
}
