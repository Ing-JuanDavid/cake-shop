package com.juan.cakeshop.api.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class RateDto {
    @Min(value = 0, message = "score must be at least 0")
    @Max(value = 5, message = "score can't exceed 5")
    private Integer score;
    @NotBlank(message = "comment can't be empty")
    private String comment;
}
