package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {
    private int productId;
    private String name;
    private int price;
    private int quant;
    private int stock;
    private String img;
}
