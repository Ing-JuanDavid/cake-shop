package com.juan.cakeshop.api.dto.requests;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class RateDto {
    private Integer score;
    private String comment;
}
