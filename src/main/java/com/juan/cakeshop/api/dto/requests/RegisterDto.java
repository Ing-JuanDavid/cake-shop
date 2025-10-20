package com.juan.cakeshop.api.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;

@Builder
@Data
public class RegisterDto {
    @NotBlank(message = "email can't be empty")
    @Email(message = "format is invalid")
    private String email;
    @NotBlank(message = "password can't be empty")
    private String password;
    @NotBlank(message = "name can't be empty")
    private String name;
    @Past(message = "date birth must be in the past")
    private LocalDate birth;
    @NotNull(message = "nip is required")
    private Long nip;
    private String sex;
    @NotBlank(message = "address can't be empty")
    private String address;
    @NotBlank(message = "telf can't be empty")
    private String telf;
}
