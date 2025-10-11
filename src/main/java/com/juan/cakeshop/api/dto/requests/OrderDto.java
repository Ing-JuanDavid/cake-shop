package com.juan.cakeshop.api.dto.requests;

import com.juan.cakeshop.api.dto.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDto {
    @NotNull(message = "Order status can't be null")
    OrderStatus orderStatus;
}
