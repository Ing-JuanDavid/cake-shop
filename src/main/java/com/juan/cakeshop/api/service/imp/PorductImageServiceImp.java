package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.requests.ProductImageDto;
import com.juan.cakeshop.api.dto.responses.ProductImageResponse;
import com.juan.cakeshop.api.mapper.ProductImageMapper;
import com.juan.cakeshop.api.model.ProductImage;
import com.juan.cakeshop.api.repository.ProductImageRepository;
import com.juan.cakeshop.api.service.ProductImageService;
import com.juan.cakeshop.exception.customExceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PorductImageServiceImp implements ProductImageService {

    final ProductImageRepository productImageRepository;
    final ProductImageMapper productImageMapper;

    @Override
    public ProductImageResponse updateProductImage(Boolean isMain, int productImageId) {

        ProductImage savedImage =  productImageRepository.findById((productImageId))
                .orElseThrow(
                        ()-> new CategoryNotFoundException(productImageId) //change this for image exception
                );


        productImageRepository.clearMainProductImage(savedImage.getProduct().getProductId(), savedImage.getImageId());
        savedImage.setIsMain(isMain);

        return productImageMapper.toResponse(productImageRepository.save(savedImage));
    }
}
