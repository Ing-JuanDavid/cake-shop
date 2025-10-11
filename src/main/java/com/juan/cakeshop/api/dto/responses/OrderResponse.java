package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Integer OrderId;
    private List<CartResponse> products;
    private Integer total;
    private String status;
    private LocalDate date;
}
