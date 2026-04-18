package com.juan.cakeshop.api.dto.responses;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAddressResponse {
    private int addressId;
    private String city;
    private String department;
    private String addressLine;
    private String description;
    long nip;
}
