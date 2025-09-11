package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private Integer categoryId;
    private String name;
    private Integer productsNumber;
}
