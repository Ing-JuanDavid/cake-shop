package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.requests.CategoryFilterDto;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.mapper.CategoryMapper;
import com.juan.cakeshop.api.dto.requests.CategoryDto;
import com.juan.cakeshop.api.dto.responses.CategoryResponse;
import com.juan.cakeshop.api.mapper.ProductMapper;
import com.juan.cakeshop.api.model.Category;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.repository.CategoryRepository;
import com.juan.cakeshop.api.service.CategoryService;
import com.juan.cakeshop.api.specifications.CategorySpecification;
import com.juan.cakeshop.api.specifications.ProductSpecification;
import com.juan.cakeshop.exception.customExceptions.CategoryAlreadyExistsException;
import com.juan.cakeshop.exception.customExceptions.CategoryNotFoundException;
import com.juan.cakeshop.exception.customExceptions.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    final CategoryRepository categoryRepository;
    final CategoryMapper categoryMapper;
    final ProductMapper productMapper;
    final CloudinaryServiceImp cloudinaryServiceImp;

    @Override
    public CategoryResponse createCategory(CategoryDto categoryDto) {
        String imgUrl;
        if(categoryRepository.existsByName(categoryDto.getName())) throw new CategoryAlreadyExistsException();

        Category category = categoryMapper.toEntity(categoryDto);

        if(categoryDto.getImg() != null && ! categoryDto.getImg().isEmpty()) {
            imgUrl = cloudinaryServiceImp.uploadedImg(categoryDto.getImg(), "folder_2");
            category.setImgUrl(imgUrl);
        }

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

        if(category.getImgUrl() != null && ! category.getImgUrl().isEmpty())
            cloudinaryServiceImp.deleteImg("folder_2", category.getImgUrl());

        categoryRepository.delete(category);

        return categoryMapper.toResponse(category);
    }

    @Override
    public PaginatedResponse<CategoryResponse> getCategories(int currentPage, int sizePage, CategoryFilterDto filters) {
        if(currentPage < 1) throw  new InvalidInputException("currentPage");

        if(sizePage < 1) throw  new InvalidInputException("sizePage");

        Specification<Category> spec = Specification
                        .where(CategorySpecification.hasName(filters.getName()))
                        .and(CategorySpecification.hasMaxProducts(filters.getMaxProducts()))
                        .and(CategorySpecification.hasMinProducts(filters.getMinProducts()));

        // create pageable — Spring handles startIndex/endIndex for you
        Pageable pageable = PageRequest.of(currentPage-1, sizePage);

        Page<Category> page = categoryRepository.findAll(spec, pageable);

        if (page.isEmpty()) return categoryMapper.toPaginatedResponse(page, 1);

        // validate page exists
        if (currentPage > page.getTotalPages()) throw new InvalidInputException("currentPage");

        return categoryMapper.toPaginatedResponse(page, currentPage);
    }
}
