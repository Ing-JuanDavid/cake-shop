package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.ProductMapper;
import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.model.Category;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.repository.CategoryRepository;
import com.juan.cakeshop.api.repository.ProductRepository;
import com.juan.cakeshop.api.service.ProductService;
import com.juan.cakeshop.exception.customExceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CloudinaryServiceImp cloudinaryServiceImp;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse createProduct(ProductDto productDto) {

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategoryId()));

        if (productDto.getImg() == null || productDto.getImg().isEmpty()) {
            throw new InvalidProductImageException();
        }

        String imageUrl = cloudinaryServiceImp.uploadedImg(productDto.getImg(), "folder_1");

        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);
        product.setImg(imageUrl);

        productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse deleteProduct(int productId) {
        if(productId <= 0) throw new InvalidProductIdException();

        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException(productId)
        );

        boolean deleted = cloudinaryServiceImp.deleteImg("folder_1",product.getImg());

        if(! deleted) throw new CloudinaryException("Failed to delete product's image from cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);

        productRepository.delete(product);

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(int productId, ProductDto productDto) {

        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException(productId));

        var productDtoCategoryId = productDto.getCategoryId();
        Category category = savedProduct.getCategory();

        if(!Objects.equals(productDtoCategoryId, savedProduct.getCategory().getCategoryId()))
            category = categoryRepository.findById(productDtoCategoryId).orElseThrow(()->new CategoryNotFoundException(productDtoCategoryId));

        String imgUrl = savedProduct.getImg();

        if(productDto.getImg() != null &&  !productDto.getImg().isEmpty()){
            if(imgUrl != null)
                cloudinaryServiceImp.deleteImg("folder_1", imgUrl);
            imgUrl = cloudinaryServiceImp.uploadedImg(productDto.getImg(), "folder_1");
        }

        productMapper.updateFromDto(productDto, savedProduct);
        savedProduct.setCategory(category);
        savedProduct.setImg(imgUrl);

        Product updateProduct = productRepository.save(savedProduct);

        return productMapper.toResponse(updateProduct);
    }

    @Override
    public ProductResponse getProduct(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException(productId)
        );

        return productMapper.toResponse(product);
    }


}
