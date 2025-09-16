package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartProductResponse {
    private String productName;
    private int quant;
}
