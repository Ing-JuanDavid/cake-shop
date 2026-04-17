package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.UserAddressDto;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.UserAddressResponse;
import com.juan.cakeshop.api.model.UserDetailsImp;
import com.juan.cakeshop.api.service.UserAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("addresses")
@RestController
@RequiredArgsConstructor
public class UserAddressController {

final UserAddressService userAddressService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<UserAddressResponse>> create(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp,
            @RequestBody @Valid UserAddressDto addressDto)
    {
        return ResponseEntity.ok(
                GenericResponse.<UserAddressResponse>builder()
                        .ok(true)
                        .data(userAddressService.createAddress(userDetailsImp.getUser(), addressDto))
                        .build()
        );
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{addressId}")
    public ResponseEntity<GenericResponse<UserAddressResponse>> update(
        @PathVariable("addressId") int addressId,
        @RequestBody @Valid UserAddressDto addressDto,
        @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        return ResponseEntity.ok(
                GenericResponse.<UserAddressResponse>builder()
                        .ok(true)
                        .data(userAddressService.updateAddress(
                                userDetailsImp.getUser().getNip(),
                                addressId,
                                addressDto
                        ))
                        .build()
        );
    }

    @GetMapping("/users/{nip}/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<List<UserAddressResponse>>> getAddressesByUser(
            @PathVariable("nip") long nip)
    {
        return ResponseEntity.ok(
                GenericResponse.<List<UserAddressResponse>>builder()
                        .ok(true)
                        .data(userAddressService.getAddresses(nip))
                        .build()
        );
    }

    @GetMapping("/user/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<List<UserAddressResponse>>> getUserAddresses(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        return ResponseEntity.ok(
                GenericResponse.<List<UserAddressResponse>>builder()
                        .ok(true)
                        .data(userAddressService.getAddresses(userDetailsImp.getUser().getNip()))
                        .build()
        );
    }


    @GetMapping("/user/default")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<UserAddressResponse>> getUserDefaultAddress(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        return ResponseEntity.ok(
                GenericResponse.<UserAddressResponse>builder()
                        .ok(true)
                        .data(userAddressService.getDefaultAddress(userDetailsImp.getUser().getNip()))
                        .build()
        );
    }

    @GetMapping("/user/default/set/{addressId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<UserAddressResponse>> setUserDefaultAddress(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp,
            @PathVariable("addressId") int addressId
    )
    {
        return ResponseEntity.ok(
                GenericResponse.<UserAddressResponse>builder()
                        .ok(true)
                        .data(userAddressService.setDefaultAddress(userDetailsImp.getUser().getNip(), addressId))
                        .build()
        );
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Map<String, String>> delete(
        @PathVariable("addressId") int addressId,
        @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        userAddressService.deleteAddress(userDetailsImp.getUser().getNip(), addressId);
        return ResponseEntity.ok(Map.of("message", "Direccion eliminada"));
    }

}
