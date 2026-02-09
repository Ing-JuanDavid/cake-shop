package com.juan.cakeshop.api.dto.responses;

import com.juan.cakeshop.api.dto.requests.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private UserResponse user;
    private String token;
}
