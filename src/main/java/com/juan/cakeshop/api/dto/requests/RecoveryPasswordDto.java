package com.juan.cakeshop.api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class RecoveryPasswordDto {
    @NotBlank
    private String resetToken;
    @NotBlank
    private String newPassword;
}
