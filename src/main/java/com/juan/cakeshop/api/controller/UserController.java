package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.UserDto;
import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<List<UserResponse>>> getUsers()
    {
        return ResponseEntity.ok(GenericResponse.<List<UserResponse>>builder()
                .message("ok")
                .data(userService.getUsers())
                .build());
    }

    @GetMapping("/{nip}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<UserResponse>> getUser(@PathVariable Long nip)
    {
        return ResponseEntity.ok(GenericResponse.<UserResponse>builder()
                .message("ok")
                .data(userService.findUser(nip))
                .build());
    }

    @PutMapping("/{nip}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<UserResponse>> updateUser(
            @PathVariable Long nip,
            @RequestBody @Valid UserDto userDto)
    {
        return ResponseEntity.ok(GenericResponse.<UserResponse>builder()
                .message("ok")
                .data(userService.updateUser(nip, userDto))
                .build());
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<UserResponse>> updateUser(
            @AuthenticationPrincipal String email,
            @RequestBody @Valid UserInfoDto userInfoDto)
    {
        return ResponseEntity.ok(GenericResponse.<UserResponse>builder()
                .message("ok")
                .data(userService.updateUser(email, userInfoDto))
                .build());
    }

    @DeleteMapping("/{nip}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<UserResponse>> deleteUser(@PathVariable Long nip)
    {
        return ResponseEntity.ok(GenericResponse.<UserResponse>builder()
                .message("ok")
                .data(userService.deleteUserByNip(nip))
                .build());
    }

}
