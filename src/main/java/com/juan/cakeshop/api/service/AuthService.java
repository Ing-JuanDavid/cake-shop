package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.LoginDto;
import com.juan.cakeshop.api.dto.requests.PasswordDto;
import com.juan.cakeshop.api.dto.requests.RegisterDto;
import com.juan.cakeshop.api.dto.responses.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterDto registerDto);

    AuthResponse login(LoginDto loginDto);

    AuthResponse changePassword(PasswordDto passwordDto);
}
