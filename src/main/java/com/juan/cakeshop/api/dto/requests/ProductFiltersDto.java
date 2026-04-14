package com.juan.cakeshop.api.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductFiltersDto {
    private String name;
    private String category;
    private Integer minPrice;
    private Integer maxPrice;
    private Boolean available;
}
