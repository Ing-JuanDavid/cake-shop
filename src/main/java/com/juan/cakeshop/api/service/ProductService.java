package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.requests.ProductFiltersDto;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductDto productDto);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByCategory(int categoryId);

    ProductResponse deleteProduct(int productId);

    ProductResponse updateProduct(int productId, ProductDto productDto);

    ProductResponse getProduct(int productId);

    PaginatedResponse<ProductResponse> getProducts(int currentPage, int sizePage, ProductFiltersDto filters);
}
