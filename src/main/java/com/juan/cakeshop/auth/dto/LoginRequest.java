package com.juan.cakeshop.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    @Email(message = "Format is invalid")
    private String email;
    @NotBlank
    private String password;
}
