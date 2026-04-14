package com.juan.cakeshop.api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryDto {
    @NotBlank(message = "name can't be empty")
    private String name;
    private MultipartFile img;
}
