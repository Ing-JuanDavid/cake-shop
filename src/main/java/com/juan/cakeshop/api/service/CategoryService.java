package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;
import com.juan.cakeshop.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();

    CategoryResponse createCategory(CategoryDto categoryDto);

    CategoryResponse updateCategory(int categoryId, CategoryDto categoryDto);

}
