package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.requests.ProductFiltersDto;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.mapper.ProductMapper;
import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.model.Category;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.model.ProductImage;
import com.juan.cakeshop.api.repository.CategoryRepository;
import com.juan.cakeshop.api.repository.ProductImageRepository;
import com.juan.cakeshop.api.repository.ProductRepository;
import com.juan.cakeshop.api.service.ProductService;
import com.juan.cakeshop.api.specifications.ProductSpecification;
import com.juan.cakeshop.exception.customExceptions.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CloudinaryServiceImp cloudinaryServiceImp;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new CategoryNotFoundException(categoryId)
        );

        return productMapper.products(category.getProducts());
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductDto productDto) {

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategoryId()));

        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);

         if(productDto.getImages() != null && !productDto.getImages().isEmpty())
         {
             List<ProductImage> images = this.uploadProductImageList(productDto.getImages(), product);
             productImageRepository.saveAll(images);
             product.setProductImages(images);
         }

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse deleteProduct(int productId) {
        if(productId <= 0) throw new InvalidProductIdException();

        Product savedProduct = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException(productId)
        );

        List<ProductImage> existingImages = savedProduct.getProductImages();
        if(! existingImages.isEmpty()) {
            cloudinaryServiceImp.deleteImageList(existingImages);
        }

        productRepository.delete(savedProduct);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(int productId, ProductDto productDto, boolean overWriteImages) {

        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (!Objects.equals(productDto.getCategoryId(), savedProduct.getCategory().getCategoryId())) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategoryId()));
            savedProduct.setCategory(category);
        }

        productMapper.updateFromDto(productDto, savedProduct);

        List<ProductImage> existingImages = savedProduct.getProductImages();

        if (productDto.getImages() != null && !productDto.getImages().isEmpty()) {

            if(overWriteImages) {
                cloudinaryServiceImp.deleteImageList(existingImages);
                existingImages.clear();
            }

            existingImages.addAll(uploadProductImageList(productDto.getImages(), savedProduct));
        }

        Product updatedProduct = productRepository.save(savedProduct);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public ProductResponse getProduct(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException(productId)
        );

        return productMapper.toResponse(product);
    }

    @Override
    public PaginatedResponse<ProductResponse> getProducts(int currentPage, int sizePage, ProductFiltersDto filters) {
        // validations
        if (currentPage < 1) throw new InvalidInputException("currentPage");
        if (sizePage < 1) throw new InvalidInputException("sizePage");

        Specification<Product> spec = Specification
                        .where(ProductSpecification.hasName(filters.getName()))
                        .and(ProductSpecification.hasCategory(filters.getCategory()))
                        .and(ProductSpecification.hasMinPrice(filters.getMinPrice()))
                        .and(ProductSpecification.hasMaxPrice(filters.getMaxPrice()))
                        .and(ProductSpecification.hasAvailable(filters.getAvailable())
                );

        // create pageable — Spring handles startIndex/endIndex for you
        Pageable pageable = PageRequest.of(currentPage-1, sizePage);

        // DB only fetches the products you need
        Page<Product> page = productRepository.findAll(spec, pageable);

        if (page.isEmpty()) return productMapper.toPaginatedResponse(page, 1);

        // validate page exists
        if (currentPage > page.getTotalPages()) throw new InvalidInputException("currentPage");

        return productMapper.toPaginatedResponse(page, currentPage);
    }

    @Override
    public List<ProductImage> uploadProductImageList(List<MultipartFile> images, Product product) {

        return cloudinaryServiceImp.uploadImageList(images, "products").stream()
                .map(cr -> {
                    return ProductImage.builder()
                            .publicId(cr.getPublicId())
                            .imageUrl(cr.getImgUrl())
                            .product(product)
                            .build();
                }).toList();
    }


}
