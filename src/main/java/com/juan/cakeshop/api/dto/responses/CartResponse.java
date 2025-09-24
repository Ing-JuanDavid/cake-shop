package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {
    private String productName;
    private int price;
    private int quant;
}
