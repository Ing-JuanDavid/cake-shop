package com.juan.cakeshop.api.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    @Email(message = "Format is invalid")
    private String email;
    @NotBlank
    private String password;
}
