package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
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
public class CategoryController {

    final CategoryService categoryService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<CategoryResponse>> createCategory(
            @RequestBody @Valid CategoryDto categoryDto
    )
    {
        return ResponseEntity.ok(GenericResponse.<CategoryResponse>builder()
                .message("ok")
                .data(categoryService.createCategory(categoryDto))
                .build());
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<CategoryResponse>>> getAllCategories()
    {
        return ResponseEntity.ok(GenericResponse.<List<CategoryResponse>>builder()
                .message("ok")
                .data(categoryService.getAllCategories())
                .build());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<GenericResponse<CategoryResponse>> getCategory(@PathVariable int categoryId)
    {
        return ResponseEntity.ok(GenericResponse.<CategoryResponse>builder()
                .message("ok")
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
                .message("ok")
                .data(categoryService.updateCategory(categoryId, categoryDto))
                .build());
    }

    // Delete category ...
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<GenericResponse<CategoryResponse>> deleteCategory(@PathVariable  int categoryId)
    {
        return ResponseEntity.ok(GenericResponse.<CategoryResponse>builder()
                .message("ok")
                .data(categoryService.deleteCategory(categoryId))
                .build());
    }

}
