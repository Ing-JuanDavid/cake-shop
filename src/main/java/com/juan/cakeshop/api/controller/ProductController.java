package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.requests.ProductFiltersDto;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<GenericResponse<ProductResponse>> createProduct(
            @ModelAttribute @Valid ProductDto productDto
    ) {
        ProductResponse productResponse = productService.createProduct(productDto);
        return ResponseEntity.ok(GenericResponse.<ProductResponse>builder()
                .ok(true)
                .data(productResponse)
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<List<ProductResponse>>> getAllProducts()
    {
        return ResponseEntity.ok(GenericResponse.<List<ProductResponse>>builder()
                .ok(true)
                .data(productService.getAllProducts())
                .build());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GenericResponse<ProductResponse>> getProduct(@PathVariable int productId)
    {
        return ResponseEntity.ok(GenericResponse.<ProductResponse>builder()
                .ok(true)
                .data(productService.getProduct(productId))
                .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<GenericResponse<ProductResponse>> updateProduct(
            @PathVariable  int productId,
            @ModelAttribute @Valid ProductDto productDto,
            @RequestParam(defaultValue = "false") boolean overWriteImages
    )
    {
        ProductResponse productResponse = productService.updateProduct(productId, productDto, overWriteImages);

        return ResponseEntity.ok(GenericResponse.<ProductResponse>builder()
                .ok(true)
                .data(productResponse)
                .build());
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<GenericResponse<List<ProductResponse>>> getProductsByCategory(
            @PathVariable int categoryId
    )
    {
        return ResponseEntity.ok(
                GenericResponse.<List<ProductResponse>>builder()
                        .ok(true)
                        .data(productService.getProductsByCategory(categoryId))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<GenericResponse<ProductResponse>> deleteProduct(
            @PathVariable int productId,
            @RequestParam(defaultValue = "false") boolean delete // it is not going to delete from database
    )
    {
        ProductResponse productResponse = productService.deleteProduct(productId, delete);
        return ResponseEntity.ok(GenericResponse.<ProductResponse>builder()
                .ok(true)
                .data(productResponse)
                .build());
    }


    @GetMapping
    public ResponseEntity<GenericResponse<PaginatedResponse<ProductResponse>>> getProducts(
            @RequestParam(required = false, defaultValue = "1") int currentPage,
            @RequestParam(required = false, defaultValue = "5") int sizePage,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) Boolean active
    )
    {
        ProductFiltersDto filters = ProductFiltersDto.builder()
                .name(name)
                .category(category)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .available(available)
                .isActive(active)
                .build();

        return ResponseEntity.ok(GenericResponse.<PaginatedResponse<ProductResponse>>builder()
                .ok(true)
                .data(productService.getProducts(currentPage, sizePage, filters))
                .build());
    }

}
