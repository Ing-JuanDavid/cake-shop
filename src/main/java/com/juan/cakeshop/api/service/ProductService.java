package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ProductService {
    ResponseEntity<Map<String, Object>> createProduct(ProductDto product);

    List<ProductResponse> getAllProducts();

    ResponseEntity<Map<String, Object>> deleteProduct(int id);
}
