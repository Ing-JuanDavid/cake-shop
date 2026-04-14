package com.juan.cakeshop.api.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryFilterDto {
    private String name;
    private Integer minProducts;
    private Integer maxProducts;

}
