package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.model.Category;
import com.juan.cakeshop.api.service.CategoryService;
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

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<CategoryResponse>> createCategory(CategoryDto categoryDto)
    {
        return ResponseEntity.ok(GenericResponse.<CategoryResponse>builder()
                .message("ok")
                .data(categoryService.createCategory(categoryDto))
                .build());
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories()
    {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<CategoryResponse>> updateCategory(
            @PathVariable int categoryId,
            @RequestBody CategoryDto categoryDto
    )
    {
        return ResponseEntity.ok(GenericResponse.<CategoryResponse>builder()
                .message("ok")
                .data(categoryService.updateCategory(categoryId, categoryDto))
                .build());
    }

    // Delete category ...


}
