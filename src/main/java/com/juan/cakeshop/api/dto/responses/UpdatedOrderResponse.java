package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UpdatedOrderResponse {
    Integer orderId;
    String status;
    LocalDate updateDate;
}
