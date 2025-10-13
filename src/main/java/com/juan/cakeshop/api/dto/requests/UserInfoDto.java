package com.juan.cakeshop.api.dto.requests;

import com.juan.cakeshop.api.model.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

    @Data
    @Builder public class UserInfoDto {
        @NotBlank(message = "name can't be empty")
        private String name;
        @NotNull(message = "birth can't be empty")
        private LocalDate birth;
        @NotBlank(message = "sex can't be empty")
        private String sex;
        @NotBlank(message = "address can't be empty")
        private String address;
        @NotBlank(message = "telf can't be empty")
        private String telf;
    }
