package com.juan.cakeshop.auth;

import com.juan.cakeshop.api.model.Rol;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.auth.dto.AuthDto;
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

    public User toEntity(AuthDto userDto)
    {
        if(userDto.getEmail() == null || userDto.getEmail().isBlank()) throw new InvalidInputException("email");

        if(userDto.getPassword() ==  null || userDto.getPassword().isBlank()) throw new InvalidInputException("password");

        return User.builder()
                .email(userDto.getEmail())
                .pass(passwordEncoder.encode(userDto.getPassword()))
                .nip(userDto.getNip())
                .name(userDto.getName())
                .birth(userDto.getBirth())
                .sex(userDto.getSex())
                .rol(Rol.USER)
                .telf(userDto.getTelf())
                .address(userDto.getAddress())
                .build();
    }

    public AuthResponse toResponse(UserDetails user)
    {
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
