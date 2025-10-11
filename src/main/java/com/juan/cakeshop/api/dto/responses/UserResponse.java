package com.juan.cakeshop.api.dto.responses;

import com.juan.cakeshop.api.model.Rol;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserResponse {
    private Long nip;
    private String email;
    private String name;
    private LocalDate birth;
    private Rol rol;
    private String sex;
    private String address;
    private String telf;
}
