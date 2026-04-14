package com.juan.cakeshop.api.dto.requests;

import lombok.Builder;
import lombok.Data;

import javax.management.relation.Role;

@Data
@Builder
public class UserFilterDto {
    private Long nip;
    private String name;
    private String email;
    private String role;
    private Boolean isAccountNonLocked;
}
