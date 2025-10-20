package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;
import com.juan.cakeshop.api.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public Category toEntity(CategoryDto categoryDto){
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }

    public CategoryResponse toResponse(Category category)
    {
        int productNumber = category.getProducts() != null
                ? category.getProducts().size()
                : 0;

        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .productsNumber(productNumber)
                .build();
    }

    public Category updateCategoryFromDto(Category savedCategory, CategoryDto categoryDto) {
        savedCategory.setName(categoryDto.getName());
        return  savedCategory;
    }
}
