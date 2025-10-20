package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.LoginDto;
import com.juan.cakeshop.api.dto.requests.PasswordDto;
import com.juan.cakeshop.api.dto.requests.RegisterDto;
import com.juan.cakeshop.api.dto.responses.AuthResponse;
import com.juan.cakeshop.api.service.imp.AuthServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    final AuthServiceImp authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterDto registerDto)
    {
        return ResponseEntity.ok(authService.register(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginDto request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/changePass")
    public ResponseEntity<AuthResponse> changePass(@RequestBody PasswordDto request)
    {
        return ResponseEntity.ok(authService.changePassword(request));
    }


}
