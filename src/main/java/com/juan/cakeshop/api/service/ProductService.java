package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.requests.ProductFiltersDto;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.model.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductDto productDto);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByCategory(int categoryId);

    ProductResponse deleteProduct(int productId);

    // add addImages as a new boolean parameter (true for adding, false for overwriting the images)
    ProductResponse updateProduct(int productId, ProductDto productDto, boolean overWriteImages);

    ProductResponse getProduct(int productId);

    PaginatedResponse<ProductResponse> getProducts(int currentPage, int sizePage, ProductFiltersDto filters);

    List<ProductImage> uploadProductImageList(List<MultipartFile> files, Product product);
}
