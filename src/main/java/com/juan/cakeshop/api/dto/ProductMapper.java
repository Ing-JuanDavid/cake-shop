package com.juan.cakeshop.api.dto;

import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMapper {
    public Product toEntity(ProductDto productDto)
    {
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quant(productDto.getCant())
                .build();
    }

    public ProductResponse toResponse(Product product)
    {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .cant(product.getQuant())
                .categoryName(product.getCategory().getName())
                .score(product.getScore())
                .imgUrl(product.getImg())
                .build();
    }

    public List<ProductResponse> products(List<Product> products)
    {
        return products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateFromDto(ProductDto productDto, Product savedProduct) {
        savedProduct.setName(productDto.getName());
        savedProduct.setPrice(productDto.getPrice());
        savedProduct.setDescription(productDto.getDescription());
        savedProduct.setQuant(productDto.getCant());
    }
}
