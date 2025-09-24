package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.CategoryMapper;
import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;
import com.juan.cakeshop.api.model.Category;
import com.juan.cakeshop.api.repository.CategoryRepository;
import com.juan.cakeshop.api.service.CategoryService;
import com.juan.cakeshop.exception.customExceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    final CategoryRepository categoryRepository;
    final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategory(int categoryId)
    {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new CategoryNotFoundException(categoryId)
        );

        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(int categoryId, CategoryDto categoryDto) {

        Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new CategoryNotFoundException(categoryId)
        );

        savedCategory = categoryMapper.updateCategoryFromDto(savedCategory, categoryDto);

        Category updatedCategory = categoryRepository.save(savedCategory);

        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public CategoryResponse deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new CategoryNotFoundException(categoryId)
        );

        categoryRepository.delete(category);

        return categoryMapper.toResponse(category);
    }
}
