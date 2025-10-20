package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.requests.RegisterDto;
import com.juan.cakeshop.api.dto.responses.AuthResponse;
import com.juan.cakeshop.api.mapper.AuthMapper;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.api.dto.requests.LoginDto;
import com.juan.cakeshop.api.dto.requests.PasswordDto;
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
public class AuthServiceImp implements com.juan.cakeshop.api.service.AuthService {

    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final UserDetailsService userDetailsService;
    final AuthMapper authMapper;

    public AuthResponse register(RegisterDto registerDto)
    {
        if(userRepository.existsByEmail(registerDto.getEmail()))
            throw new UserAlreadyExistException("email", registerDto.getEmail());

        if(userRepository.existsByNip(registerDto.getNip()))
            throw new UserAlreadyExistException("NIP", String.valueOf(registerDto.getNip()));

        User user = authMapper.toEntity(registerDto);

        userRepository.save(user);
        return authMapper.toResponse(user);
    }

    public AuthResponse login(LoginDto loginDto)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(loginDto.getEmail());

        return authMapper.toResponse(user);
    }

    public AuthResponse changePassword(PasswordDto passwordDto)
    {

        if(passwordDto.getEmail() == null || passwordDto.getEmail().isBlank()) throw new InvalidInputException("email");

        if(passwordDto.getPass() == null || passwordDto.getPass().isBlank()) throw new InvalidInputException("password");

        User user = userRepository.findByEmail(passwordDto.getEmail()).orElseThrow(
                ()->new UsernameNotFoundException("user not found")
        );

        user.setPass(passwordEncoder.encode(passwordDto.getPass()));

        User updatedUser = userRepository.save(user);

        return authMapper.toResponse(updatedUser);
    }
}
