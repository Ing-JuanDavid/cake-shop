package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private Integer productId;
    private String name;
    private Integer price;
    private Integer cant;
    private String categoryName;
    private String imgUrl;
}
