package com.juan.cakeshop.api.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {
    private int productId;
    @NotNull(message = "quant is required")
    @Positive(message = "quant must be greater than 0")
    private int quant;
}
