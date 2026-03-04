package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.UserDto;
import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.model.UserDetailsImp;
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
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<List<UserResponse>>> getUsers()
    {
        return ResponseEntity.ok(GenericResponse.<List<UserResponse>>builder()
                .ok(true)
                .data(userService.getUsers())
                .build());
    }

    @GetMapping("/{nip}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<UserResponse>> getUser(@PathVariable Long nip)
    {
        return ResponseEntity.ok(GenericResponse.<UserResponse>builder()
                .ok(true)
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
                .ok(true)
                .data(userService.updateUser(nip, userDto))
                .build());
    }


    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<UserResponse>> getUserInfo(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        return ResponseEntity.ok(
                GenericResponse.<UserResponse>builder()
                        .ok(true)
                        .data(userService.getUserInfo(userDetailsImp))
                        .build()
        );
    }


    @PutMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<UserResponse>> updateUser(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp,
            @RequestBody @Valid UserInfoDto userInfoDto)
    {
        return ResponseEntity.ok(GenericResponse.<UserResponse>builder()
                .ok(true)
                .data(userService.updateUser(userDetailsImp.getUsername(), userInfoDto))
                .build());
    }

    @DeleteMapping("/{nip}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<UserResponse>> deleteUser(@PathVariable Long nip)
    {
        return ResponseEntity.ok(GenericResponse.<UserResponse>builder()
                .ok(true)
                .data(userService.deleteUserByNip(nip))
                .build());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/lock/{nip}")
    public ResponseEntity<GenericResponse<UserResponse>> lockUser(@PathVariable long nip)
    {
        return ResponseEntity.ok(GenericResponse.<UserResponse>builder()
                .ok(true)
                .data(userService.lockUser(nip))
                .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/unlock/{nip}")
    public ResponseEntity<GenericResponse<UserResponse>> unLockUser(@PathVariable long nip)
    {
        return ResponseEntity.ok(GenericResponse.<UserResponse>builder()
                .ok(true)
                .data(userService.unLockUser(nip))
                .build());
    }


}
