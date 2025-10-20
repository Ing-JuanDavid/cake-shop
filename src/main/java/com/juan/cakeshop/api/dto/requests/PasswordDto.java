package com.juan.cakeshop.api.dto.requests;

import lombok.Data;

@Data
public class PasswordDto {
    private String email;
    private String pass;
}
