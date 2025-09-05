package com.juan.cakeshop.api.dto.requests;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ProductDto {
    private String name;
    private Integer price;
    private String description;
    private Integer cant;
    private Integer categoryId;
    private MultipartFile img;
}
