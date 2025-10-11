package com.juan.cakeshop.auth;

import com.juan.cakeshop.auth.dto.LoginRequest;
import com.juan.cakeshop.auth.dto.PasswordRequest;
import com.juan.cakeshop.auth.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserDto request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/changePass")
    public ResponseEntity<AuthResponse> changePass(@RequestBody PasswordRequest request)
    {
        return ResponseEntity.ok(authService.changePassword(request));
    }


}
