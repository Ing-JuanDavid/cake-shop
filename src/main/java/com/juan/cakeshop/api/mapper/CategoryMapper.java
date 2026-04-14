package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .imgUrl(category.getImgUrl())
                .productsNumber(productNumber)
                .build();
    }

    public Category updateCategoryFromDto(Category savedCategory, CategoryDto categoryDto) {
        savedCategory.setName(categoryDto.getName());
        return  savedCategory;
    }

    public List<CategoryResponse> categories(List<Category> categories)
    {
        return categories.stream()
                .map(this::toResponse)
                .toList();
    }

    public PaginatedResponse<CategoryResponse> toPaginatedResponse(Page<Category> page, int currentPage)
    {
        return PaginatedResponse.<CategoryResponse>builder()
                .totalPages(page.getTotalPages())
                .pageLength(page.getNumberOfElements())
                .totalElements((int) page.getTotalElements())
                .nextPage(page.hasNext()? ++currentPage : currentPage)
                .data(this.categories(page.getContent()))
                .build();
    }
}
