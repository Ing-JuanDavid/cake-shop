package com.juan.cakeshop.api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
    @NotBlank(message = "name can't be empty")
    private String name;
}
