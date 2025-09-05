package com.juan.cakeshop.auth;

import com.juan.cakeshop.api.model.Rol;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.auth.dto.LoginRequest;
import com.juan.cakeshop.auth.dto.PasswordRequest;
import com.juan.cakeshop.auth.dto.RegisterRequest;
import com.juan.cakeshop.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepo;
    final UserDetailsService userDetailsService;
    final JwtService jwtService;

    public AuthResponse register(RegisterRequest request)
    {
        var user = User.builder()
                .nip(request.getNip())
                .email(request.getEmail())
                .pass(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .birth(request.getBirth())
                .sex(request.getSex())
                .rol(Rol.USER)
                .telf(request.getTelf())
                .address(request.getAddress())
                .build();

        userRepo.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public AuthResponse login(LoginRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        return AuthResponse.builder()
                .token(jwtService.getToken(userDetailsService.loadUserByUsername(request.getEmail())))
                .build();
    }

    public AuthResponse changePassword(PasswordRequest request)
    {
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("user not found"));
        System.out.println("Continue");

        var userUpdated = User.builder()
                .nip(user.getNip())
                .email(user.getEmail())
                .name(user.getName())
                .pass(passwordEncoder.encode(request.getPass()))
                .rol(user.getRol())
                .telf(user.getTelf())
                .sex(user.getSex())
                .birth(user.getBirth())
                .address(user.getAddress())
                .build();
        userRepo.save(userUpdated);

        return AuthResponse.builder()
                .token(jwtService.getToken(userUpdated))
                .build();
    }
}
