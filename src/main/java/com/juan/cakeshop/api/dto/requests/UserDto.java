package com.juan.cakeshop.api.dto.requests;

import com.juan.cakeshop.api.model.Rol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserDto {
    private String name;
    private LocalDate birth;
    private Rol rol;
    private String sex;
    private String address;
    private String telf;
}
