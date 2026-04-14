package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.requests.CategoryFilterDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.ProductResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryDto categoryDto);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategory(int categoryId);

    CategoryResponse updateCategory(int categoryId, CategoryDto categoryDto);

    CategoryResponse deleteCategory(int categoryId);

    PaginatedResponse<CategoryResponse> getCategories(int currentPage, int sizePage, CategoryFilterDto filters);

}
