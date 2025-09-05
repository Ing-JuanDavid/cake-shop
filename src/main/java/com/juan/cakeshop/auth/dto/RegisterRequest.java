package com.juan.cakeshop.auth.dto;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;

@Builder
@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private LocalDate birth;
    private Long nip;
    private String sex;
    private String address;
    private String telf;
}
