package com.juan.cakeshop.api.dto.responses;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImageResponse {
    private Integer imageId;
    private String imageUrl;
    private Boolean isMain;

    public ProductImageResponse(Integer imageId, String imageUrl, Boolean isMain) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
    }
}
