package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.mapper.ProductMapper;
import com.juan.cakeshop.api.model.Category;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.model.ProductImage;
import com.juan.cakeshop.api.repository.CategoryRepository;
import com.juan.cakeshop.api.repository.ProductImageRepository;
import com.juan.cakeshop.api.repository.ProductRepository;
import com.juan.cakeshop.api.service.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImpTests {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductImageRepository productImageRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CloudinaryServiceImp cloudinaryServiceImp;

    @InjectMocks
    private ProductServiceImp productServiceImp;

    @Test
    void shouldCreateAProductWithoutImages()
    {
        //arr
        ProductDto productDto  = ProductDto.builder()
                .categoryId(1)
                .images(null)
                .build();

        Category category = new Category();
        category.setCategoryId(1);

        Product product = new Product();
        product.setProductId(10);
        product.setName("cake");

        ProductResponse productResponse = ProductResponse.builder().build();

        when(categoryRepository.findById(productDto.getCategoryId()))
                .thenReturn(Optional.of(category));

        when(productMapper.toEntity(productDto)).thenReturn(product);

        when(productRepository.save(product)).thenReturn(product);

        when(productMapper.toResponse(product)).thenReturn(productResponse);

        // act
        ProductResponse result = productServiceImp.createProduct(productDto);

        // assert
        assertEquals(productResponse, result);

        verify(categoryRepository).findById(1);
        verify(productMapper).toEntity(productDto);
        verify(productRepository).save(product);
        verify(productMapper).toResponse(product);

        //  importante
        verifyNoInteractions(productImageRepository);
        verifyNoInteractions(cloudinaryServiceImp);
    }

//    @Test
//    void shouldThrowWhenCloudinaryFails()
//    {
//        Category category = new Category();
//        category.setCategoryId(1);
//
//        MultipartFile img = mock(MultipartFile.class);
//
//        ProductDto productDto  = ProductDto.builder()
//                .categoryId(1)
//                .images(List.of(img))
//                .build();
//
//
//
//        Product product = new Product();
//        product.setProductId(10);
//        product.setName("cake");
//
//        ProductResponse productResponse = ProductResponse.builder().build();
//
//        when(categoryRepository.findById(productDto.getCategoryId()))
//                .thenReturn(Optional.of(category));
//
//        when(productMapper.toEntity(productDto)).thenReturn(product);
//
//        when(productRepository.save(product)).thenReturn(product);
//
//        when(cloudinaryServiceImp.uploadedImg(any(), any()))
//                .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload product's img"));
//
//        ResponseStatusException ex = assertThrows(ResponseStatusException.class, ()->{
//            productServiceImp.createProduct(productDto);
//        });
//
//        assertEquals("Failed to upload product's img", ex.getReason());
//
//        verify(productRepository, never()).save(any()); // sí se guardó antes del fallo
//        verify(productImageRepository, never()).saveAll(any());
//    }

    @Test
    void shouldMakeSoftDelete()
    {
        // arrange
        int productId = 2;
        boolean delete = false; //it isn't going to delete

        Product savedProduct = Product.builder()
                .productId(productId)
                .name("chocolate cake")
                .build();

        ProductResponse response = ProductResponse
                .builder()
                .productId(productId)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(savedProduct));

        ProductResponse result = productServiceImp.deleteProduct(productId, delete);

        assertEquals(productId, response.getProductId());
        verify(cloudinaryServiceImp, never()).deleteImageList(anyList());
        verify(productRepository, never()).delete(savedProduct);
    }

    @Test
    void shouldMakeDelete()
    {
        // arrange
        int productId = 2;
        boolean delete = true; //it's going to delete

        List<ProductImage> images = new ArrayList<>(List.of(ProductImage.builder().imageId(2).build()));

        Product savedProduct = Product.builder()
                .productId(productId)
                .name("chocolate cake")
                .productImages(images)
                .build();

        ProductResponse response = ProductResponse
                .builder()
                .productId(productId)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(savedProduct));
        when(productMapper.toResponse(any(Product.class))).thenReturn(response);
        ProductResponse result = productServiceImp.deleteProduct(productId, delete);

        assertEquals(productId, result.getProductId());
        verify(cloudinaryServiceImp).deleteImageList(anyList());
        verify(productRepository).delete(savedProduct);
    }


}
