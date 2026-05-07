package com.juan.cakeshop.api.dto.responses;

import com.juan.cakeshop.api.model.Rate;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse {
    private Integer productId;
    private String name;
    private Integer price;
    private Integer quant;
    private String description;
    private String categoryName;
    private Float score;
    private List<ProductImageResponse> images;
    private List<RateResponse> rates;
    private boolean isActive;
}
