package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserSimpleResponse {
    private Long nip;
    private String email;
    private String name;
    private LocalDate birth;
    private List<String> roles;
    private String sex;
    private String address;
    private String telf;
    private boolean accountNonLocked;
}
