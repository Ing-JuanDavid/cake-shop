package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.*;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.dto.responses.ProfileInfo;
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


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<ProfileInfo>> createUser(
            @RequestBody @Valid UserRegisterDto userRegisterDto
    )
    {
        return ResponseEntity.ok(
                GenericResponse.<ProfileInfo>builder()
                        .ok(true)
                        .data(userService.createUser(userRegisterDto))
                        .build()
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<List<ProfileInfo>>> getUsers()
    {
        return ResponseEntity.ok(GenericResponse.<List<ProfileInfo>>builder()
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
    public ResponseEntity<GenericResponse<ProfileInfo>> updateUser(
            @PathVariable Long nip,
            @RequestBody @Valid UserDto userDto)
    {
        return ResponseEntity.ok(GenericResponse.<ProfileInfo>builder()
                .ok(true)
                .data(userService.updateUser(nip, userDto))
                .build());
    }


    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<ProfileInfo>> getProfileInfo(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        return ResponseEntity.ok(
                GenericResponse.<ProfileInfo>builder()
                        .ok(true)
                        .data(userService.getUserInfo(userDetailsImp))
                        .build()
        );
    }


    @PutMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<ProfileInfo>> updateUser(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp,
            @RequestBody @Valid UserInfoDto userInfoDto)
    {
        return ResponseEntity.ok(GenericResponse.<ProfileInfo>builder()
                .ok(true)
                .data(userService.updateUser(userDetailsImp.getUsername(), userInfoDto))
                .build());
    }

    @DeleteMapping("/{nip}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<ProfileInfo>> deleteUser(@PathVariable Long nip)
    {
        return ResponseEntity.ok(GenericResponse.<ProfileInfo>builder()
                .ok(true)
                .data(userService.deleteUserByNip(nip))
                .build());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/lock/{nip}")
    public ResponseEntity<GenericResponse<ProfileInfo>> lockUser(@PathVariable long nip)
    {
        return ResponseEntity.ok(GenericResponse.<ProfileInfo>builder()
                .ok(true)
                .data(userService.lockUser(nip))
                .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/unlock/{nip}")
    public ResponseEntity<GenericResponse<ProfileInfo>> unLockUser(@PathVariable long nip)
    {
        return ResponseEntity.ok(GenericResponse.<ProfileInfo>builder()
                .ok(true)
                .data(userService.unLockUser(nip))
                .build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<GenericResponse<PaginatedResponse<ProfileInfo>>> getUsers(
            @RequestParam (required = false, defaultValue = "1") Integer currentPage,
            @RequestParam (required = false, defaultValue = "5")Integer sizePage,
            @RequestParam (required = false) String name,
            @RequestParam (required = false) String email,
            @RequestParam (required = false) Long nip,
            @RequestParam (required = false) String role,
            @RequestParam (required = false) Boolean accountNonLocked
    )
    {

        UserFilterDto filters = UserFilterDto.builder()
                .name(name)
                .email(email)
                .nip(nip)
                .role(role)
                .isAccountNonLocked(accountNonLocked)
                .build();

        return ResponseEntity.ok(GenericResponse.<PaginatedResponse<ProfileInfo>>builder()
                .ok(true)
                .data(userService.getUserss(currentPage, sizePage, filters))
                .build());
    }


}
