package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.ProductMapper;
import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.model.Category;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.repository.CategoryRepository;
import com.juan.cakeshop.api.repository.ProductRepository;
import com.juan.cakeshop.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CloudinaryServiceImp cloudinaryServiceImp;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();

        if(products.isEmpty()) return productResponses;

        productResponses = productMapper.products(products);

        return productResponses;
    }

    @Override
    public ResponseEntity<Map<String, Object>> createProduct(ProductDto request) {
        // 1. Validar existencia de categoría
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 2. Validar que haya imagen
        if (request.getImg() == null || request.getImg().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Image is required"));
        }

        try {
            // 3. Subir imagen a Cloudinary
            String imageUrl = cloudinaryServiceImp.uploadedImg(request.getImg(), "folder_1");
            if (imageUrl == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Error uploading image"));
            }

            // 4. Mapear y guardar producto
            Product product = productMapper.toEntity(request);
            product.setCategory(category);
            product.setImg(imageUrl);

            productRepository.save(product);

            // 5. Devolver respuesta
            return ResponseEntity.ok(Map.of("product", productMapper.toResponse(product)));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Unexpected error: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleteProduct(int id) {
        if(id <= 0) return ResponseEntity.badRequest()
                .body(Map.of("error", "Id must be greater than 0"));

        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product, not found")
        );

        boolean deleted = cloudinaryServiceImp.deleteImg("folder_1",product.getImg());

        if(! deleted){
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to delete product img from cloudinary"));
        }

        productRepository.delete(product);

        return ResponseEntity.ok(Map.of(
                "message", "Product deleted successfully",
                "productId", id));
    }
}
