package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<GenericResponse<ProductResponse>> createProduct(
            @ModelAttribute ProductDto productDto
    ) {
        ProductResponse productResponse = productService.createProduct(productDto);
        return ResponseEntity.ok(GenericResponse.<ProductResponse>builder()
                .message("ok")
                .data(productResponse)
                .build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts()
    {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GenericResponse<ProductResponse>> getProduct(@PathVariable int productId)
    {
        return ResponseEntity.ok(GenericResponse.<ProductResponse>builder()
                .message("ok")
                .data(productService.getProduct(productId))
                .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<GenericResponse<ProductResponse>> updateProduct(
            @PathVariable  int productId,
            @ModelAttribute ProductDto productDto
    )
    {
        ProductResponse productResponse = productService.updateProduct(productId, productDto);

        return ResponseEntity.ok(GenericResponse.<ProductResponse>builder()
                .message("ok")
                .data(productResponse)
                .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<GenericResponse<ProductResponse>> deleteProduct(@PathVariable int productId)
    {
        ProductResponse productResponse = productService.deleteProduct(productId);
        return ResponseEntity.ok(GenericResponse.<ProductResponse>builder()
                .message("ok")
                .data(productResponse)
                .build());
    }


}
