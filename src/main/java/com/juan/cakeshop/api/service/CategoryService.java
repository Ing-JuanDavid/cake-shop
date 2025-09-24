package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryDto categoryDto);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategory(int categoryId);

    CategoryResponse updateCategory(int categoryId, CategoryDto categoryDto);

    CategoryResponse deleteCategory(int categoryId);

}
