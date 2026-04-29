package com.juan.cakeshop.api.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CloudinaryResponse {
    private String imgUrl;
    private String publicId;
}
