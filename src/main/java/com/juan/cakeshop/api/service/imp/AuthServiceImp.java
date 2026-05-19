package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.requests.RecoveryPasswordDto;
import com.juan.cakeshop.api.dto.requests.RegisterDto;
import com.juan.cakeshop.api.dto.responses.AuthResponse;
import com.juan.cakeshop.api.mapper.AuthMapper;
import com.juan.cakeshop.api.model.PasswordResetToken;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.model.UserDetailsImp;
import com.juan.cakeshop.api.repository.PasswordResetTokenRepository;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.api.dto.requests.LoginDto;
import com.juan.cakeshop.api.dto.requests.PasswordDto;
import com.juan.cakeshop.api.service.EmailService;
import com.juan.cakeshop.exception.customExceptions.InvalidInputException;
import com.juan.cakeshop.exception.customExceptions.InvalidPasswordResetTokenException;
import com.juan.cakeshop.exception.customExceptions.UserAlreadyExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements com.juan.cakeshop.api.service.AuthService {

    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final PasswordResetTokenRepository passwordResetTokenRepository;
    final UserDetailsService userDetailsService;
    final EmailService emailService;
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
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword())
        );

        UserDetailsImp user = (UserDetailsImp) auth.getPrincipal();
        return authMapper.toResponse(user.getUser());
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

    @Override
    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null) {
            return;
        }

        String resetToken = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(resetToken)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .isUsed(false)
                .build();
        passwordResetTokenRepository.save(passwordResetToken);

        emailService.sendPasswordResetLink(user.getEmail(), "http://localhost:4200/auth/recovery-password?token="+resetToken);
    }

    @Override
    @Transactional
    public AuthResponse recoveryPassword(RecoveryPasswordDto recoveryPasswordDto) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository
                .findByTokenAndIsUsedFalse(recoveryPasswordDto.getResetToken()).orElseThrow(
                        ()-> new InvalidPasswordResetTokenException("Token invalido")
                );

        if(LocalDateTime.now().isAfter(passwordResetToken.getExpiresAt())) {
            throw new InvalidPasswordResetTokenException("Token invalido");
        }

        User user = passwordResetToken.getUser();
        user.setPass(passwordEncoder.encode(recoveryPasswordDto.getNewPassword()));
        passwordResetToken.setIsUsed(true);
        passwordResetTokenRepository.save(passwordResetToken);

        return authMapper.toResponse(userRepository.save(user));
    }
}
