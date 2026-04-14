package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.requests.ProductDto;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.model.Product;
import org.springframework.data.domain.Page;
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
                .quant(productDto.getQuant())
                .build();
    }

    public ProductResponse toResponse(Product product)
    {

        int rateNumber = product.getRates().size();

        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .quant(product.getQuant())
                .description(product.getDescription())
                .categoryName(product.getCategory().getName())
                .score(product.getScore())
                .imgUrl(product.getImg())
                .rateNumber(rateNumber)
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
        savedProduct.setQuant(productDto.getQuant());
    }

    public PaginatedResponse<ProductResponse> toPaginatedResponse(Page<Product> page, int currentPage)
    {
        return PaginatedResponse.<ProductResponse>builder()
                .currentPage(currentPage)
                .pageLength(page.getNumberOfElements())
                .totalElements((int)page.getTotalElements())
                .totalPages(page.getTotalPages())
                .nextPage(page.hasNext() ? ++currentPage : currentPage)
                .data(this.products(page.getContent()))
                .build();
    }
}
