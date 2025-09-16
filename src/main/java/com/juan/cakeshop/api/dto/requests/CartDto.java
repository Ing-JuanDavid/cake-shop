package com.juan.cakeshop.api.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartProductDto {
    private int productId;
    private int quant;
}
