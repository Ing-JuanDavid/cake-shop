package com.juan.cakeshop.api.dto.requests;

import com.juan.cakeshop.api.model.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserRegisterDto {

    @NotNull(message = "nip can't be null")
    private Long nip;
    @NotBlank(message = "email can't be empty")
    private String email;
    @NotBlank(message = "pass can't be empty")
    private String password;
    @NotBlank(message = "name can't be empty")
    private String name;
    private LocalDate birth;
    @NotNull(message = "rol can't be null")
    private Rol rol;
    private String sex;
    private String address;
    private String telf;
}
