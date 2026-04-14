package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.requests.CategoryFilterDto;
import com.juan.cakeshop.api.dto.requests.ProductFiltersDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    final CategoryService categoryService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<CategoryResponse>> createCategory(
            @ModelAttribute @Valid CategoryDto categoryDto
    )
    {
        return ResponseEntity.ok(GenericResponse.<CategoryResponse>builder()
                .ok(true)
                .data(categoryService.createCategory(categoryDto))
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<List<CategoryResponse>>> getAllCategories()
    {
        return ResponseEntity.ok(GenericResponse.<List<CategoryResponse>>builder()
                .ok(true)
                .data(categoryService.getAllCategories())
                .build());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<GenericResponse<CategoryResponse>> getCategory(@PathVariable int categoryId)
    {
        return ResponseEntity.ok(GenericResponse.<CategoryResponse>builder()
                .ok(true)
                .data(categoryService.getCategory(categoryId))
                .build());
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<CategoryResponse>> updateCategory(
            @PathVariable int categoryId,
            @RequestBody @Valid CategoryDto categoryDto
    )
    {
        return ResponseEntity.ok(GenericResponse.<CategoryResponse>builder()
                .ok(true)
                .data(categoryService.updateCategory(categoryId, categoryDto))
                .build());
    }

    // Delete category ...
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<GenericResponse<CategoryResponse>> deleteCategory(@PathVariable  int categoryId)
    {
        return ResponseEntity.ok(GenericResponse.<CategoryResponse>builder()
                .ok(true)
                .data(categoryService.deleteCategory(categoryId))
                .build());
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PaginatedResponse<CategoryResponse>>> getCategories(
            @RequestParam (required = false, defaultValue = "1") Integer currentPage,
            @RequestParam (required = false, defaultValue = "5")Integer sizePage,
            @RequestParam (required = false) String name,
            @RequestParam (required = false) Integer maxProducts,
            @RequestParam (required = false) Integer minProducts
    )
    {

        CategoryFilterDto filters = CategoryFilterDto.builder()
                .name(name)
                .maxProducts(maxProducts)
                .minProducts(minProducts)
                .build();

        return ResponseEntity.ok(GenericResponse.<PaginatedResponse<CategoryResponse>>builder()
                .ok(true)
                .data(categoryService.getCategories(currentPage, sizePage, filters))
                .build());
    }

}
