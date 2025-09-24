package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RateResponse {
    private Integer rateId;
    private String userName;
    private Integer score;
    private String comment;
    private LocalDate creationDate;
    private Integer productId;
    private String productName;
}
