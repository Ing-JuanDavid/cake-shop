package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private Integer productId;
    private String name;
    private Integer price;
    private Integer quant;
    private String categoryName;
    private Float score;
    private String imgUrl;
}
