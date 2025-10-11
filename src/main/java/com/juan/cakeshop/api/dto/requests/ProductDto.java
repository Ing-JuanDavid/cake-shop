package com.juan.cakeshop.api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ProductDto {
    @NotBlank(message = "name can't be empty")
    private String name;
    @NotNull(message = "price is required")
    @Positive(message = "invalid price")
    private Integer price;
    @NotBlank(message = "description can't be empty")
    private String description;
    @NotNull(message = "quantity is required")
    @Positive(message = "invalid quant")
    private Integer quant;
    @NotNull(message = "category id is required")
    @Positive(message = "invalid categoryId")
    private Integer categoryId;
    private MultipartFile img;
}
