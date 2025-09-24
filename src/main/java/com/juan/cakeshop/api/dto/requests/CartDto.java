package com.juan.cakeshop.api.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {
    private int productId;
    private int quant;
}
