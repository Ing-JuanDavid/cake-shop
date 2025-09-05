package com.juan.cakeshop.auth.dto;

import lombok.Data;

@Data
public class PasswordRequest {
    private String email;
    private String pass;
}
