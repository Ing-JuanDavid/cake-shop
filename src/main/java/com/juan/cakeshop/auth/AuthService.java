package com.juan.cakeshop.auth;

import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.auth.dto.LoginRequest;
import com.juan.cakeshop.auth.dto.PasswordRequest;
import com.juan.cakeshop.auth.dto.AuthDto;
import com.juan.cakeshop.exception.customExceptions.InvalidInputException;
import com.juan.cakeshop.exception.customExceptions.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final UserDetailsService userDetailsService;
    final AuthMapper authMapper;

    public AuthResponse register(AuthDto userDto)
    {
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new UserAlreadyExistException("email", userDto.getEmail());

        if(userRepository.existsByNip(userDto.getNip()))
            throw new UserAlreadyExistException("NIP", String.valueOf(userDto.getNip()));

        User user = authMapper.toEntity(userDto);

        userRepository.save(user);
        return authMapper.toResponse(user);
    }

    public AuthResponse login(LoginRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        return authMapper.toResponse(user);
    }

    public AuthResponse changePassword(PasswordRequest request)
    {

        if(request.getEmail() == null || request.getEmail().isBlank()) throw new InvalidInputException("email");

        if(request.getPass() == null || request.getPass().isBlank()) throw new InvalidInputException("password");

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()->new UsernameNotFoundException("user not found")
        );

        user.setPass(passwordEncoder.encode(request.getPass()));

        User updatedUser = userRepository.save(user);

        return authMapper.toResponse(updatedUser);
    }
}
