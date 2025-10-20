package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.requests.RegisterDto;
import com.juan.cakeshop.api.dto.responses.AuthResponse;
import com.juan.cakeshop.api.model.Rol;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.exception.customExceptions.InvalidInputException;
import com.juan.cakeshop.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthMapper {

    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;

    public User toEntity(RegisterDto registerDto)
    {
        if(registerDto.getEmail() == null || registerDto.getEmail().isBlank()) throw new InvalidInputException("email");

        if(registerDto.getPassword() ==  null || registerDto.getPassword().isBlank()) throw new InvalidInputException("password");

        return User.builder()
                .email(registerDto.getEmail())
                .pass(passwordEncoder.encode(registerDto.getPassword()))
                .nip(registerDto.getNip())
                .name(registerDto.getName())
                .birth(registerDto.getBirth())
                .sex(registerDto.getSex())
                .rol(Rol.USER)
                .telf(registerDto.getTelf())
                .address(registerDto.getAddress())
                .build();
    }

    public AuthResponse toResponse(UserDetails user)
    {
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
